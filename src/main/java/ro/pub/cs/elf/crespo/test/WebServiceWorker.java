package ro.pub.cs.elf.crespo.test;

import java.util.List;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.app.Crespo;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class WebServiceWorker extends SwingWorker<Void, User> {

	private String[] users = {"bugs", "daffy", "sam"};
	private Mediator mediator;
	private int DELAY = 2000;

	public WebServiceWorker(Mediator mediator) {
		this.mediator = mediator;
	}

	/**
	 * generate users with files
	 */
	@Override
	protected Void doInBackground() throws Exception {
		for (String user : users) {
			if (!mediator.getMe().toString().toLowerCase().contains(user)) {
				publish(Crespo.loadUser(user));
				Thread.sleep(DELAY);
			}
		}
		return null;
	}

	/**
	 * update GUI with new users and files
	 */
	@Override
	protected void process(List<User> chunks) {
		User user = chunks.get(0);
		if (user != null) {
			this.mediator.addUser(user);
		}
	}
}
