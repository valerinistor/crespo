package ro.pub.cs.elf.crespo.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ro.pub.cs.elf.crespo.dto.UserFile;

public class Sender extends Thread {

	private final SelectionKey key;

	public Sender(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		System.out.println("SEND: ");

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
				ByteBuffer buf = ByteBuffer.allocateDirect((int) fileToSend
						.length() + 10);
				buf.clear();
				buf.put(Files.readAllBytes(Paths.get(fileToSend
						.getAbsolutePath())));
				buf.put(Network.EOT);
				buf.flip();
				socketChannel.write(buf);
			} else {
				System.err.println("File not found");
				return;
			}

			key.interestOps(0);
			key.selector().wakeup();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
