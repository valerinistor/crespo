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
		System.out.println("WRITE: ");

		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocateDirect(128);

		try {
			String requestedFile = (String) key.attachment();
			List<UserFile> files = Network.mediator.getMe().getSharedFiles();
			for (UserFile file : files) {
				if (file.getName().equals(requestedFile)) {
					buf.clear();
					buf.put(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
					buf.flip();
				}
			}

			socketChannel.write(buf);

			key.interestOps(SelectionKey.OP_READ);
			key.selector().wakeup();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
