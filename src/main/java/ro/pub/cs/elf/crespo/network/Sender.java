package ro.pub.cs.elf.crespo.network;

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
				ByteBuffer buf = ByteBuffer.allocateDirect((int) fileToSend.length() + 10);
				buf.clear();
				buf.put(Files.readAllBytes(Paths.get(fileToSend
						.getAbsolutePath())));
				buf.put(Network.EOT);
				buf.flip();
				socketChannel.write(buf);
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
