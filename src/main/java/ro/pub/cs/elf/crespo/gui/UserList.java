package ro.pub.cs.elf.crespo.gui;

import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class UserList extends AbstractList<User> {

	private static final long serialVersionUID = -2019586378839251935L;

	public UserList(Mediator mediator) {
		super(mediator);
	}

	@Override
	public void execute() {
		User user = getSelectedValue();
		if (user == null) {
			return;
		}

		this.mediator.addFiles(user.getSharedFiles());
		this.mediator.updateStatus(String.format("Getting file list from %s...", user));
	}
}
