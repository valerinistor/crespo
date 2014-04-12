package ro.pub.cs.elf.crespo.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class Receiver extends Thread {

	private final SelectionKey key;

	public Receiver(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		System.out.println("READ: ");

		SocketChannel socketChannel = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocateDirect(128);
		buf.clear();
		try {
			socketChannel.read(buf);

			buf.flip();
			key.interestOps(SelectionKey.OP_WRITE);
			key.selector().wakeup();

			WritableByteChannel outChannel = Channels.newChannel(System.out);
			outChannel.write(buf);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
