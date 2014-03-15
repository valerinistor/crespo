package ro.pub.cs.elf.crespo.gui;

import javax.swing.JTable;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class TransferTable extends JTable {

	private static final long serialVersionUID = -1702407567172353002L;
	private Mediator mediator;

	public TransferTable(Mediator mediator) {
		super(new TransferTableModel());
		this.mediator = mediator;
		addTransferData();
	}

	private void addTransferData() {
		TransferData td = new TransferData();
		td.setSource("John");
		td.setDestination("Dow");
		td.setProgress(42);
		td.setFile(new File("cmd.exe"));
		td.setStatus(TransferStatus.RECEIVING);

		TransferTableModel tm = (TransferTableModel) getModel();
		tm.addRow(td);
		tm.addRow(td);
		tm.addRow(td);
		tm.addRow(td);
	}
}
