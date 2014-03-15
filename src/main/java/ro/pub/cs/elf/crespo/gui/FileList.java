package ro.pub.cs.elf.crespo.gui;

import java.util.List;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class FileList extends AbstractList<File> {

	private static final long serialVersionUID = 896209637426611146L;

	private final Mediator mediator;

	public FileList(Mediator mediator) {
		super();
		this.mediator = mediator;
	}

	public void addFiles(List<File> files) {
		for (File f : files) {
			addElement(f);
		}
	}

	@Override
	public void execute() {
		File file = getSelectedValue();
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
