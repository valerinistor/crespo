package ro.pub.cs.elf.crespo.gui;

public class UserList extends AbstractList<String> {

	private static final long serialVersionUID = -2019586378839251935L;

	private final String[] data = { "Barack", "Obama", "Putin", "Vladimir" };

	public UserList() {
		super();
		for (String s : data) {
			addElement(s);
		}
	}

}
