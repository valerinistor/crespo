package ro.pub.cs.elf.crespo.gui;

import java.util.Vector;

import javax.swing.JTable;

import ro.pub.cs.elf.crespo.app.ICommand;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class TransferTable extends JTable implements ICommand {

	private static final long serialVersionUID = -1702407567172353002L;
	private final TransferTableModel transferTableModel;
	private final Mediator mediator;

	public TransferTable(Mediator mediator) {
		super();
		this.transferTableModel = new TransferTableModel();
		this.mediator = mediator;
		setModel(this.transferTableModel);
	}

	public void addRow(TransferData rowData) {
		Vector<Object> row = new Vector<>(5);
		row.add(rowData.getSource());
		row.add(rowData.getDestination());
		row.add(rowData.getFile().getFileName());
		row.add(rowData.getProgress());
		row.add(rowData.getStatus().toString());

		this.transferTableModel.addRow(row);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		try {
			Vector<Object> row = (Vector<Object>) transferTableModel
					.getDataVector().get(getSelectedRow());
			this.mediator.updateStatus(String.format("%s", row.get(4)));
		} catch (NullPointerException e) {
		}
	}
}
