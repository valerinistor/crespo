package ro.pub.cs.elf.crespo.dto;

import java.util.List;

public class User {

	private String userName;

	private List<File> sharedFiles;

	public User(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<File> getSharedFiles() {
		return sharedFiles;
	}

	public void setSharedFiles(List<File> sharedFiles) {
		this.sharedFiles = sharedFiles;
	}

	public void addSharedFile(File file) {
		this.sharedFiles.add(file);
	}

	public void removeSharedFile(File file) {
		this.sharedFiles.remove(file);
	}
	@Override
	public String toString() {
		return this.userName;
	}
}
