package ro.pub.cs.elf.crespo.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ro.pub.cs.elf.crespo.app.Crespo;
import ro.pub.cs.elf.crespo.mediator.Mediator;

public class Draw {

	private final Container contentPane;
	private final FileList fileList;
	private final UserList userList;
	private final StatusBar statusBar;
	private final TransferTable transferTable;

	public Draw(Container contentPane, Mediator mediator) {
		this.contentPane = contentPane;
		this.fileList = new FileList(mediator);
		this.userList = new UserList(mediator);
		this.transferTable = new TransferTable(mediator);
		this.statusBar = new StatusBar();
	}

	public void paint() {
		JPanel left = new JPanel(new BorderLayout());
		JScrollPane filePane = new JScrollPane(fileList);
		JScrollPane transferPane = new JScrollPane(transferTable);
		transferPane.setPreferredSize(new Dimension(0, Crespo.height / 3));

		left.add(filePane, BorderLayout.CENTER);
		left.add(transferPane, BorderLayout.SOUTH);

		// Add panel to CENTER position in order to fill available space.
		contentPane.add(left, BorderLayout.CENTER);

		JScrollPane userPane = new JScrollPane(userList);
		userPane.setPreferredSize(new Dimension(Crespo.width / 4, 0));
		contentPane.add(userPane, BorderLayout.EAST);

		contentPane.add(statusBar, BorderLayout.SOUTH);
	}

	public UserList getUserList() {
		return userList;
	}

	public FileList getFileList() {
		return fileList;
	}
}
