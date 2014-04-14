package ro.pub.cs.elf.crespo.network;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;

import ro.pub.cs.elf.crespo.dto.UserFile;

public class Sender extends Thread {

	private Logger logger = Logger.getLogger(Sender.class);
	private final SelectionKey key;

	public Sender(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		logger.info("SENDER");

		SocketChannel socketChannel = (SocketChannel) key.channel();

		try {
			ByteBuffer buf = null;
			String requestedFile = (String) key.attachment();
			List<UserFile> files = Network.mediator.getMe().getSharedFiles();
			UserFile fileToSend = null;
			for (UserFile file : files) {
				if (file.getName().equals(requestedFile)) {
					fileToSend = file;
				}
			}

			if (fileToSend != null) {
				logger.info("sending file " + fileToSend.getPath());

				int bytesToSend = (int) (fileToSend.length()) + 1; // +1 for EOT

				//Send file size to client
				buf = ByteBuffer.allocateDirect(8);
				buf.clear();
				buf.putLong(fileToSend.length());
				buf.flip();
				socketChannel.write(buf);

				// everything is ok
				if (bytesToSend <= Network.CHUNK_SIZE) {

					buf = ByteBuffer.allocateDirect(bytesToSend);
					buf.clear();
					buf.put(Files.readAllBytes(Paths.get(fileToSend.getAbsolutePath())));
					buf.flip();
					while (buf.hasRemaining()) {
						socketChannel.write(buf);
					}
				}

				// split in chunks
				else {
					int chunks = (int) Math.ceil((double) bytesToSend / Network.CHUNK_SIZE);
					buf = ByteBuffer.allocateDirect(Network.CHUNK_SIZE);
					FileInputStream in = new FileInputStream(fileToSend);

					for (int chunk = 0; chunk < chunks; chunk++) {
						byte[] fileBuffer = new byte[Network.CHUNK_SIZE];

						in.read(fileBuffer);

						buf.clear();
						buf.put(fileBuffer);
						buf.flip();

						while (buf.hasRemaining()) {
							socketChannel.write(buf);
						}
					}

					in.close();
				}
			} else {
				logger.error("File not found");
				return;
			}

			key.interestOps(0);
			key.selector().wakeup();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
