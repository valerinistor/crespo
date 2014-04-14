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

		try {
			
			String dst = processRequest(socketChannel, Network.NAME_HEADER);
			if (dst == null) {
				return;
			}
			
			String fileName = processRequest(socketChannel, Network.REQUEST_HEADER);
			if (fileName == null) {
				return;
			}
			
			key.attach(dst + "|" + fileName);
			key.interestOps(SelectionKey.OP_WRITE);
			key.selector().wakeup();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	private String processRequest(SocketChannel socketChannel, String header) throws IOException {
		ByteBuffer buf = ByteBuffer.allocateDirect(128); 
		buf.clear();
		socketChannel.read(buf);
		buf.flip();

		byte[] bytes = new byte[buf.remaining()];
		buf.get(bytes);
		//buf.position(buf.position() - bytes.length);
		String event = new String(bytes);

		logger.info("Request: " + event);

		if (event.startsWith(header)) {
			return event.substring(header.length());
		} else {
			logger.error("Invalid request");
			return null;
		}
	}

}
