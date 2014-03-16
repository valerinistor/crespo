package ro.pub.cs.elf.crespo.test;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class NetworkWorker extends SwingWorker<File, TransferData> {

	public final TransferQueue<TransferData> transferQueue = new LinkedTransferQueue<>();
	private final Mediator mediator;

	public NetworkWorker(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	protected File doInBackground() throws Exception {

		while (true) {
			TransferData task = transferQueue.take();
			for (int i = 0; i <= 100; i += 10) {
				task.setProgress(i);
				this.mediator.updateTransfers(task);
				Thread.sleep(1000);
			}
		}
	}

	public void addNetworkTask(TransferData task) {
		transferQueue.tryTransfer(task);
	}
}
