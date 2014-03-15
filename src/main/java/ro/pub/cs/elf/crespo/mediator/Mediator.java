package ro.pub.cs.elf.crespo.mediator;

import java.util.List;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.gui.CrespoWorker;
import ro.pub.cs.elf.crespo.gui.Draw;

public class Mediator {

	private Draw draw;
	private final User me;
	private CrespoWorker worker;

	// TODO add network and web service layer

	public Mediator() {
		this.me = new User("_me_");
		this.worker = new CrespoWorker(this);
	}

	public void registerDraw(Draw draw) {
		this.draw = draw;
	}

	public void addUser(User user) {
		this.draw.getUserList().addElement(user);
	}

	public void addFiles(List<File> files) {
		this.draw.getFileList().clearModel();
		this.draw.getFileList().addFiles(files);
	}

	public void addTransfer(TransferData rowData) {
		this.draw.getTransferTable().addRow(rowData);
	}

	public void updateStatus(String status) {
		this.draw.getStatusBar().setText(status);
	}

	public User getMe() {
		return me;
	}

	public void runWorker() {
		worker.execute();
	}
}
