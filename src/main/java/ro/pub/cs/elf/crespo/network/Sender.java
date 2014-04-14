package ro.pub.cs.elf.crespo.network;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.dto.User;
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
			String event = (String) key.attachment();
			String[] split = event.split("@");
			String requestedFile = split[0];
			String dst = split[1];

			List<UserFile> files = Network.mediator.getMe().getSharedFiles();
			UserFile fileToSend = null;
			for (UserFile file : files) {
				if (file.getName().equals(requestedFile)) {
					fileToSend = file;
				}
			}

			if (fileToSend != null) {
				logger.info("sending file " + fileToSend.getPath());

				// add transfer to gui
				final TransferData td = addTransfer(fileToSend, dst);

				int bytesToSend = (int) (fileToSend.length()) + 1; // +1 for EOT

				// Send file size to client
				buf = ByteBuffer.allocateDirect(8);
				buf.clear();
				buf.putLong(fileToSend.length());
				buf.flip();
				socketChannel.write(buf);

				// everything is ok
				if (bytesToSend <= Network.CHUNK_SIZE) {

					buf = ByteBuffer.allocateDirect(bytesToSend);
					buf.clear();
					buf.put(Files.readAllBytes(Paths.get(fileToSend
							.getAbsolutePath())));
					buf.flip();
					while (buf.hasRemaining()) {
						socketChannel.write(buf);
					}

					td.setProgress(100);
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							Network.mediator.updateTransfers(td);
						}
					});
				}

				// split in chunks
				else {
					int chunks = (int) Math.ceil((double) bytesToSend
							/ Network.CHUNK_SIZE);
					buf = ByteBuffer.allocateDirect(Network.CHUNK_SIZE);
					FileInputStream in = new FileInputStream(fileToSend);

					for (int chunk = 0; chunk < chunks; chunk++) {
						byte[] fileBuffer = new byte[Network.CHUNK_SIZE];

						int bytesRead = in.read(fileBuffer);

						buf.clear();
						buf.put(fileBuffer, 0, bytesRead);
						buf.flip();

						while (buf.hasRemaining()) {
							socketChannel.write(buf);
						}

						td.setProgress((int) ((chunk + 1) * 100f / chunks));
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								Network.mediator.updateTransfers(td);
							}
						});
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

	public TransferData addTransfer(UserFile file, String dst) {
		TransferData td = new TransferData();
		td.setSource(Network.mediator.getMe());
		td.setDestination(new User(dst));
		td.setFile(file);
		td.setProgress(0);
		td.setStatus(TransferStatus.SENDING);

		Network.mediator.addTransfer(td);
		return td;
	}
}
