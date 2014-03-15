package ro.pub.cs.elf.crespo.gui;

import javax.swing.JTable;

public class TransferTable extends JTable {

	public enum TransferStatus {
		SENDING, RECEIVING, COMPLETED
	}

	private static final long serialVersionUID = -1702407567172353002L;

	private static String[] columnNames = { "Source", "Destination",
			"File name", "Progress", "Status" };

	private static Object[][] data = {
			{ "Kathy", "Smith", "Snowboarding", new Integer(5),
					new Boolean(false) },
			{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
			{ "Sue", "Black", "Knitting", new Integer(2), new Boolean(false) },
			{ "Jane", "White", "Speed reading", new Integer(20),
					new Boolean(true) },
			{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) },
			{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) },
			{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) },
			{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

	public TransferTable() {
		super(data, columnNames);
	}

}
