package ro.pub.cs.elf.crespo.gui;

import java.awt.Dimension;

import javax.swing.JLabel;

import ro.pub.cs.elf.crespo.app.Crespo;

public class StatusBar extends JLabel {

	private static final long serialVersionUID = -3413535550127125719L;

	public StatusBar() {
		super("Status");
		super.setPreferredSize(new Dimension(Crespo.width, Crespo.height / 10));
	}

}
