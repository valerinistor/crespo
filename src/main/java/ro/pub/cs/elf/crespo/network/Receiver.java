package ro.pub.cs.elf.crespo.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class Receiver extends Thread {

	private Logger logger = Logger.getLogger(Receiver.class);
	private final SelectionKey key;

	public Receiver(SelectionKey key) {
		this.key = key;
	}

	/**
	 * Runnable
	 * Receive data from socker
	 */
	@Override
	public void run() {
		logger.info("RECEIVER");

		SocketChannel socketChannel = (SocketChannel) key.channel();

		try {
			key.attach(processRequest(socketChannel));
			key.interestOps(SelectionKey.OP_WRITE);
			key.selector().wakeup();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Extract user name and file name from request
	 * @param socketChannel
	 * @return
	 * @throws IOException
	 */
	private String processRequest(SocketChannel socketChannel) throws IOException {
		ByteBuffer buf = ByteBuffer.allocateDirect(128);
		buf.clear();
		socketChannel.read(buf);
		buf.flip();

		byte[] bytes = new byte[buf.remaining()];
		buf.get(bytes);
		String event = new String(bytes);

		logger.info("Request: " + event);

		if (event != null && !event.isEmpty() && event.contains("@")) {
			return event;
		} else {
			logger.error("Invalid request");
			return null;
		}
	}

}
