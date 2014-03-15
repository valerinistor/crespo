package ro.pub.cs.elf.crespo.mediator;

import ro.pub.cs.elf.crespo.gui.Draw;

public class Mediator {

	private Draw	draw;

	// TODO add network and web service layer

	public void registerDraw(Draw draw) {
		this.draw = draw;
	}
}
