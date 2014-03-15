package ro.pub.cs.elf.crespo.gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ro.pub.cs.elf.crespo.app.ICommand;

public class CommandListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() instanceof ICommand) {
			((ICommand) e.getSource()).execute();
		}
	}
}
