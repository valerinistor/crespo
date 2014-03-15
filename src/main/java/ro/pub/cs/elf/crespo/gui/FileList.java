package ro.pub.cs.elf.crespo.gui;

public class FileList extends AbstractList<String> {

	private static final long serialVersionUID = 896209637426611146L;

	private final String[] data = { "lorem.txt", "ipsum.mp3" };

	public FileList() {
		super();
		for (String s : data) {
			addElement(s);
		}
	}
}
