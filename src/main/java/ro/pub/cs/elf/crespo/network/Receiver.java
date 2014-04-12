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

	@Override
	public void run() {
		logger.info("RECEIVER");

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

			logger.info("Request: " + requestedFile);

			if (requestedFile.startsWith(Network.REQUEST_HEADER)) {
				requestedFile = requestedFile.substring(Network.REQUEST_HEADER
						.length());
				key.attach(requestedFile);
			} else {
				logger.error("Invalid request");
				return;
			}

			key.interestOps(SelectionKey.OP_WRITE);
			key.selector().wakeup();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
