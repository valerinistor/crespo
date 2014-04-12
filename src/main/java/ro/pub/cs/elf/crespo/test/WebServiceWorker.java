package ro.pub.cs.elf.crespo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class WebServiceWorker extends SwingWorker<Void, User> {

	private Mediator mediator;
	private int DELAY = 2000;
	private int count = 5;

	public WebServiceWorker(Mediator mediator) {
		this.mediator = mediator;
	}

	/**
	 * generate users with files
	 */
	@Override
	protected Void doInBackground() throws Exception {
		Thread.sleep(DELAY);

		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			User user = new User("user_" + i);
			List<UserFile> userFiles = new ArrayList<UserFile>();

			for (int j = 0; j < rand.nextInt(15); j++) {
				UserFile file = new UserFile(user + "_file_" + j);
				file.setOwner(user);
				userFiles.add(file);
			}
			user.setSharedFiles(userFiles);

			publish(user);
			Thread.sleep(DELAY);
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
