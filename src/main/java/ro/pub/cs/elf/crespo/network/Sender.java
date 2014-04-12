package ro.pub.cs.elf.crespo.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

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
		buf.clear();
		buf.put("Banana".getBytes());
		buf.flip();
		try {
			socketChannel.write(buf);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		key.interestOps(SelectionKey.OP_READ);
		key.selector().wakeup();
	}
}
