package ro.pub.cs.elf.crespo.gui;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class FileList extends AbstractList<File> {

	private static final long serialVersionUID = 896209637426611146L;

	private final String[] data = {"lorem.txt", "ipsum.mp3"};

	private Mediator mediator;

	public FileList(Mediator mediator) {
		super();
		this.mediator = mediator;
		for (String s : data) {
			addElement(new File(s));
		}
	}

	@Override
	public void execute() {
		// TODO
	}
}
