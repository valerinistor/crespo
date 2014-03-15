package ro.pub.cs.elf.crespo.gui;

import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import ro.pub.cs.elf.crespo.dto.TransferData;

public class TransferTableModel extends DefaultTableModel {

	private static String[] columnNames = {"Source", "Destination",
			"File name", "Progress", "Status"};

	private List<TransferData> transfers;

	public TransferTableModel() {
		super(null, columnNames);
	}

	public void addRow(TransferData rowData) {
		Vector<Object> row = new Vector<>(5);
		row.add(rowData.getSource());
		row.add(rowData.getDestination());
		row.add(rowData.getFile().getFileName());
		row.add(rowData.getProgress());
		row.add(rowData.getStatus().toString());

		addRow(row);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
