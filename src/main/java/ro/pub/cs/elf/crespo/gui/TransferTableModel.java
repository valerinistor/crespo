package ro.pub.cs.elf.crespo.gui;

import javax.swing.table.DefaultTableModel;

public class TransferTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 8624084372793071113L;

	private static String[] columnNames = { "Source", "Destination",
			"File name", "Progress", "Status" };

	public TransferTableModel() {
		super(null, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
