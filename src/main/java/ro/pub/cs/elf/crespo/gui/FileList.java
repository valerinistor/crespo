package ro.pub.cs.elf.crespo.gui;

import java.util.List;

import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class FileList extends AbstractList<UserFile> {

	private static final long serialVersionUID = 896209637426611146L;

	public FileList(Mediator mediator) {
		super(mediator);
	}

	public void addFiles(List<UserFile> files) {
		for (UserFile f : files) {
			addElement(f);
		}
	}

	/**
	 * When file is double clicked in file list
	 * new transfer is added to transfer table
	 */
	@Override
	public void execute() {
		UserFile file = getSelectedValue();

		if (file == null) {
			return;
		}

		TransferData rowData = new TransferData();
		rowData.setSource(file.getOwner());
		rowData.setDestination(this.mediator.getMe());
		rowData.setFile(file);
		rowData.setProgress(0);
		rowData.setStatus(TransferStatus.RECEIVING);

		this.mediator.addTransfer(rowData);
		this.mediator.updateStatus(file.toString());
	}
}
