package ro.pub.cs.elf.crespo.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ro.pub.cs.elf.crespo.app.ICommand;

public class CommandListener extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof FileList) {
			if (e.getClickCount() == 2) {
				((FileList) e.getSource()).execute();
			}
		} else if (e.getSource() instanceof ICommand) {
			((ICommand) e.getSource()).execute();
		}
	}
}
