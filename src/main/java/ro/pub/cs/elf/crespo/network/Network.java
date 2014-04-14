package ro.pub.cs.elf.crespo.network;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class Network {

	private Logger logger = Logger.getLogger(Network.class);
	public static Mediator mediator;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	public static String REQUEST_HEADER = "[GET]";
	public static int CHUNK_SIZE = 8192;
	public static ExecutorService pool = Executors.newFixedThreadPool(5);

	public Network(Mediator mediator) {
		Network.mediator = mediator;
	}

	public void sendRequest(TransferData td) {
		Socket socket = null;
		DataOutputStream outStream = null;

		try {
			socket = new Socket(td.getSource().getIpAddress(), td.getSource().getPort());
			outStream = new DataOutputStream(socket.getOutputStream());

			//send request for a file
			outStream.write((REQUEST_HEADER + td.getFile().getName()).getBytes());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		if (socket == null) {
			logger.info("user is offline");
			return;
		}

		// receive requested file
		receiveResponse(socket, td.getFile().getName());
		try {
			if (outStream != null) {
				outStream.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void receiveResponse(Socket socket, String fileName) {
		DataInputStream inStream = null;
		OutputStream outStream = null;

		try {
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new BufferedOutputStream(new FileOutputStream(fileName));

			// get number of bytes to receive
			long bytesToReceive = inStream.readLong();
			long bytesReceived = 0;
			byte[] fileBuffer;

			while (bytesReceived < bytesToReceive) {
				fileBuffer = new byte[Network.CHUNK_SIZE];
				int bytesRead = inStream.read(fileBuffer);
				outStream.write(fileBuffer, 0, bytesRead);
				bytesReceived += bytesRead;
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (outStream != null) {
					outStream.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public void start() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(
					new InetSocketAddress(mediator.getMe().getIpAddress(),
							mediator.getMe().getPort()));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				// wait for something to happen
				selector.select();

				// iterate over the events
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext();) {
					// get current event and REMOVE it from the list!!!
					SelectionKey key = it.next();
					it.remove();

					if (key.isAcceptable()) {
						accept(key);
					} else if (key.isReadable()) {
						key.interestOps(0);
						pool.execute(new Receiver(key));
					} else if (key.isWritable()) {
						key.interestOps(0);
						pool.execute(new Sender(key));
					}
				}
			}

		} catch (IOException e) {
			logger.fatal(e.getMessage());
		} finally {
			logger.info("closing resources");
			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}

			if (serverSocketChannel != null)
				try {
					serverSocketChannel.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		}

	}

	private void accept(SelectionKey key) throws IOException {
		logger.info("ACCEPT CONNECTION");

		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel;
		socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(key.selector(), SelectionKey.OP_READ);

		logger.info("FROM: " + socketChannel.socket().getRemoteSocketAddress());
	}
}
