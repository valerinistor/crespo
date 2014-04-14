package ro.pub.cs.elf.crespo.mediator;

import java.util.List;

import org.apache.log4j.Logger;

import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.gui.Draw;
import ro.pub.cs.elf.crespo.network.Network;
import ro.pub.cs.elf.crespo.test.WebServiceWorker;

/**
 * Mediator class which encapsulate
 * GUI client, Web Service and Network Layer
 */
public class Mediator {

	private Logger logger = Logger.getLogger(Network.class);
	private Draw draw;
	private User me; /* logged user */
	private final WebServiceWorker wSworker; /* swing worker which simulate Web Service */
	private final Network nwk;

	public Mediator() {
		this.wSworker = new WebServiceWorker(this);
		this.nwk = new Network(this);
	}

	/**
	 * register GUI client
	 * @param draw
	 */
	public void registerDraw(Draw draw) {
		this.draw = draw;
	}

	/**
	 * register logged user
	 * @param user
	 */
	public void registerMe(User me) {
		this.me = me;
	}

	/**
	 * add user to GUI user list
	 * @param user
	 */
	public void addUser(User user) {
		this.draw.getUserList().addElement(user);
	}

	/**
	 * add files list to GUI
	 * @param files
	 */
	public void addFiles(List<UserFile> files) {
		this.draw.getFileList().clearList();
		this.draw.getFileList().addFiles(files);
	}

	/**
	 * add new transfer to transfer table
	 * and also to network layer
	 * @param rowData
	 */
	public void addTransfer(TransferData rowData) {
		if (!this.draw.getTransferTable().existsInProgress(rowData)) {
			this.draw.getTransferTable().addRow(rowData);
			if (rowData.getStatus() == TransferStatus.RECEIVING)
				this.nwk.sendRequest(rowData);
		} else {
			logger.info("transfer already exists");
		}
	}

	/**
	 * update status bar label
	 * @param status
	 */
	public void updateStatus(String status) {
		this.draw.updateStatus(status);
	}

	/**
	 * update existing transfer with new data
	 * @param rowData
	 */
	public void updateTransfers(TransferData rowData) {
		this.draw.getTransferTable().updateRow(rowData);
	}

	/**
	 * return logged user
	 * @return
	 */
	public User getMe() {
		return me;
	}

	/**
	 * execute swing workers
	 */
	public void runWorkers() {
		wSworker.execute();
		nwk.start();
	}
}
