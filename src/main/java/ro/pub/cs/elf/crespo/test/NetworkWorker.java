package ro.pub.cs.elf.crespo.test;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class NetworkWorker extends SwingWorker<Void, Void> {

	public final TransferQueue<TransferData> transferQueue = new LinkedTransferQueue<>();
	private final Mediator mediator;

	public NetworkWorker(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	protected Void doInBackground() throws Exception {

		while (!isCancelled()) {
			TransferData task = transferQueue.take();
			for (int i = 0; i <= 100; i += 10) {
				task.setProgress(i);
				this.mediator.updateTransfers(task);
				Thread.sleep(1000);
			}
		}
		return null;
	}

	public void addNetworkTask(TransferData task) {
		transferQueue.offer(task);
	}
}
