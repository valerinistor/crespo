package ro.pub.cs.elf.crespo.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.pub.cs.elf.crespo.mediator.Mediator;

public class Network {

	private Mediator mediator;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	public static ExecutorService pool = Executors.newFixedThreadPool(5);

	public Network(Mediator mediator) {
		this.mediator = mediator;
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
				for (Iterator<SelectionKey> it = selector.selectedKeys()
						.iterator(); it.hasNext();) {
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
			System.err.println(e.getMessage());
		} finally {
			// cleanup
			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {
				}

			if (serverSocketChannel != null)
				try {
					serverSocketChannel.close();
				} catch (IOException e) {
				}
		}

	}

	private void accept(SelectionKey key) throws IOException {
		System.out.print("ACCEPT: ");

		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
				.channel();
		SocketChannel socketChannel;
		socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(key.selector(), SelectionKey.OP_READ);

		System.out.println("Connection from: "
				+ socketChannel.socket().getRemoteSocketAddress());
	}
}
