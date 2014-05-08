package ro.pub.cs.elf.crespo.mediator;

import java.util.List;

import org.apache.log4j.Logger;

import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.gui.Draw;
import ro.pub.cs.elf.crespo.network.Network;
import ro.pub.cs.elf.crespo.wsclient.WSClient;

/**
 * Mediator class which encapsulate GUI client, Web Service and Network Layer
 */
public class Mediator {

	private Logger logger = Logger.getLogger(Mediator.class);
	private Draw draw;
	private User me; /* logged user */
	private WSClient wsClient; /* swing worker which simulate Web Service */
	private Network nwk;

	public Mediator() {
	}

	/**
	 * register GUI client
	 * 
	 * @param draw
	 */
	public void registerDraw(Draw draw) {
		this.draw = draw;
	}

	/**
	 * register logged user
	 * 
	 * @param user
	 */
	public void registerMe(User me) {
		this.me = me;
	}

	/**
	 * add user to GUI user list
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		this.draw.getUserList().addElement(user);
	}

	/**
	 * add files list to GUI
	 * 
	 * @param files
	 */
	public void addFiles(List<UserFile> files) {
		this.draw.getFileList().clearList();
		this.draw.getFileList().addFiles(files);
	}

	public void removeUser(User user) {
		this.draw.getUserList().removeElement(user);
	}

	/**
	 * add new transfer to transfer table and also to network layer
	 * 
	 * @param rowData
	 */
	public void addTransfer(TransferData rowData) {
		if (!this.draw.getTransferTable().existsInProgress(rowData)) {
			this.draw.getTransferTable().addRow(rowData);
			if (rowData.getStatus() == TransferStatus.RECEIVING)
				this.nwk.sendFileRequest(rowData);
		} else {
			logger.info("transfer already exists");
		}
	}

	/**
	 * update status bar label
	 * 
	 * @param status
	 */
	public void updateStatus(String status) {
		this.draw.updateStatus(status);
	}

	/**
	 * update existing transfer with new data
	 * 
	 * @param rowData
	 */
	public void updateTransfers(TransferData rowData) {
		this.draw.getTransferTable().updateRow(rowData);
	}

	/**
	 * return logged user
	 * 
	 * @return
	 */
	public User getMe() {
		return me;
	}

	public Draw getDraw() {
		return draw;
	}

	/**
	 * execute swing workers
	 */
	public void runWorkers() {
		this.wsClient = new WSClient(this);
		this.nwk = new Network(this);
		wsClient.execute();
		runLocalServer();
	}

	/**
	 * run local server to accept client connection
	 */
	public void runLocalServer() {
		nwk.start();
	}
}
