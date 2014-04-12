package ro.pub.cs.elf.crespo.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Receiver extends Thread {

	private final SelectionKey key;

	public Receiver(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		System.out.println("RECEIVE: ");

		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocateDirect(128);
		buf.clear();
		try {
			socketChannel.read(buf);

			buf.flip();
			byte[] bytes = new byte[buf.remaining()];

			buf.get(bytes);
			buf.position(buf.position() - bytes.length);

			String requestedFile = new String(bytes);

			System.out.println(requestedFile);

			if (requestedFile.startsWith(Network.REQUEST_HEADER)) {
				requestedFile = requestedFile.substring(Network.REQUEST_HEADER
						.length());
				key.attach(requestedFile);
			} else {
				System.err.println("Invalid request");
				return;
			}

			key.interestOps(SelectionKey.OP_WRITE);
			key.selector().wakeup();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
