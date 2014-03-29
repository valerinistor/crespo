package ro.pub.cs.elf.crespo.test;

import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class NetworkWorker extends SwingWorker<Void, TransferData> {

	public final TransferQueue<TransferData> transferQueue = new LinkedTransferQueue<>();
	private final Mediator mediator;

	public NetworkWorker(Mediator mediator) {
		this.mediator = mediator;
	}

	/**
	 * simulate download progress
	 */
	@Override
	protected Void doInBackground() throws Exception {

		while (!isCancelled()) {
			TransferData task = transferQueue.take();
			for (int i = 0; i <= 100; i += 10) {
				task.setProgress(i);
				publish(task);
				Thread.sleep(1000);
			}
		}
		return null;
	}

	/**
	 * updated GUI according to progress status
	 */
	@Override
	protected void process(List<TransferData> chunks) {
		TransferData td = chunks.get(0);
		if (td != null) {
			this.mediator.updateTransfers(td);
		}
	}

	/**
	 * add new transfer for network layer
	 * @param task transfer data
	 */
	public void addNetworkTask(TransferData task) {
		transferQueue.offer(task);
	}
}
