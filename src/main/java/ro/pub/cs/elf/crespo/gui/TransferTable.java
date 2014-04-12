package ro.pub.cs.elf.crespo.gui;

import java.util.Vector;

import javax.swing.JTable;

import ro.pub.cs.elf.crespo.app.ICommand;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.mediator.Mediator;

/**
 * Custom Transfer Table {@link JTable}
 *
 */
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

	/**
	 * add new transfer to transfer table
	 * @param rowData row data to add
	 */
	public void addRow(TransferData rowData) {
		Vector<Object> row = new Vector<>(5);
		row.add(rowData.getSource());
		row.add(rowData.getDestination());
		row.add(rowData.getFile());
		row.add(rowData.getProgress());
		row.add(rowData.getStatus());

		this.transferTableModel.addRow(row);
	}

	/**
	 * Update a transfer row in transfer table
	 * @param rowData new updated row data
	 */
	public void updateRow(TransferData rowData) {

		for (int row = 0; row < transferTableModel.getRowCount(); row++) {
			User source = (User) transferTableModel.getValueAt(row, 0);
			User dest = (User) transferTableModel.getValueAt(row, 1);
			UserFile file = (UserFile) transferTableModel.getValueAt(row, 2);

			if (source.equals(rowData.getSource())
					&& dest.equals(rowData.getDestination())
					&& file.equals(rowData.getFile())) {
				transferTableModel.setValueAt(rowData.getProgress(), row, 3);
				transferTableModel.setValueAt(rowData.getStatus(), row, 4);
			}
		}
		execute();
	}

	/**
	 * Update status label based on selected row in transfer table
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		if (getSelectedRow() == -1) {
			return;
		}

		Vector<Object> row = (Vector<Object>) transferTableModel
				.getDataVector().get(getSelectedRow());
		TransferStatus status = (TransferStatus) row.get(4);
		String label = "";

		switch (status) {
			case COMPLETED:
				label = String.format("Completed to download file %s from %s", row.get(2), row.get(0));
				break;
			case SENDING:
				label = String.format("Sending file %s to %s", row.get(2), row.get(1));
				break;
			case RECEIVING:
				label = String.format("Receiving file %s from %s", row.get(2), row.get(0));
				break;
			default:
				break;
		}
		mediator.updateStatus(label);
	}
}
