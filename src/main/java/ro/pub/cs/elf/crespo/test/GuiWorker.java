package ro.pub.cs.elf.crespo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.File;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class GuiWorker extends SwingWorker<User, User> {

	private int DELAY = 3000;
	private int count = 5;
	private Mediator mediator;

	public GuiWorker(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	protected User doInBackground() throws Exception {
		Thread.sleep(DELAY);

		for (int i = 0; i < count; i++) {
			User user = new User("user_" + i);
			List<File> userFiles = new ArrayList<File>();

			for (int j = 0; j < new Random().nextInt(15); j++) {
				File file = new File(user + "_file_" + j);
				file.setOwner(user);
				userFiles.add(file);
			}
			user.setSharedFiles(userFiles);

			publish(user);
			Thread.sleep(DELAY);
		}

		return null;
	}

	@Override
	protected void process(List<User> chunks) {
		User user = chunks.get(0);
		if (user != null) {
			this.mediator.addUser(user);
		}
	}
}
