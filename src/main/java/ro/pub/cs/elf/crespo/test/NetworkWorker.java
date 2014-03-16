package ro.pub.cs.elf.crespo.test;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class NetworkWorker extends SwingWorker<File, Integer> {

	private final Queue<TransferData> transferQueue;
	private final Mediator mediator;

	public NetworkWorker(Mediator mediator) {
		this.mediator = mediator;
		this.transferQueue = new LinkedList<>();
	}

	@Override
	protected File doInBackground() throws Exception {

		while (true) {
			if (!transferQueue.isEmpty()) {
				TransferData task = transferQueue.poll();
				for (int i = 0; i <= 100; i += 10) {
					task.setProgress(i);
					this.mediator.updateTransfers(task);
					Thread.sleep(1000);
				}
			}
		}
	}

	public void addNetworkTask(TransferData task) {
		this.transferQueue.add(task);
	}
}
