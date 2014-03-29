package ro.pub.cs.elf.crespo.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Custom progress bar renderer
 * in TranferTable {@link TransferTable} cell
 */
public class ProgressCellRender extends JProgressBar implements
		TableCellRenderer {

	private static final long serialVersionUID = -871103145800102582L;

	public ProgressCellRender() {
		super(0, 100);
		setStringPainted(true);
		setForeground(Color.DARK_GRAY);
		setBorder(null);
		setStringPainted(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		int progress = 0;
		if (value instanceof Float) {
			progress = Math.round(((Float) value) * 100f);
		} else if (value instanceof Integer) {
			progress = (int) value;
		}
		setValue(progress);
		setString(String.format("%d%%", progress));
		return this;
	}

}