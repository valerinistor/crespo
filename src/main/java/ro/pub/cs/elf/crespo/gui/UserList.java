package ro.pub.cs.elf.crespo.gui;

import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class UserList extends AbstractList<User> {

	private static final long serialVersionUID = -2019586378839251935L;

	private final String[] data = {"Barack", "Obama", "Putin", "Vladimir"};
	private Mediator mediator;

	public UserList(Mediator mediator) {
		super();
		this.mediator = mediator;
		for (String s : data) {
			addElement(new User(s));
		}
	}

	@Override
	public void execute() {
		// TODO
	}
}
