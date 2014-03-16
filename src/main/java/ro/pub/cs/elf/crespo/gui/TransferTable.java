package ro.pub.cs.elf.crespo.gui;

import java.util.Vector;

import javax.swing.JTable;

import ro.pub.cs.elf.crespo.app.ICommand;
import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.User;
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
		row.add(rowData.getFile());
		row.add(rowData.getProgress());
		row.add(rowData.getStatus().toString());

		this.transferTableModel.addRow(row);
	}

	public void updateRow(TransferData rowData) {

		for (int row = 0; row < transferTableModel.getRowCount(); row++) {
			User source = (User) transferTableModel.getValueAt(row, 0);
			User dest = (User) transferTableModel.getValueAt(row, 1);
			File file = (File) transferTableModel.getValueAt(row, 3);

			if (source.equals(rowData.getSource())
					&& dest.equals(rowData.getDestination())
					&& file.equals(rowData.getFile())) {
				transferTableModel.setValueAt(rowData.getProgress(), row, 3);
				transferTableModel.setValueAt(rowData.getStatus().toString(),
						row, 4);
			}
		}

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
