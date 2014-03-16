package ro.pub.cs.elf.crespo.dto;

import java.util.List;

public class User {

	private String userName;
	private List<File> sharedFiles;
	private List<TransferData> transfers;

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

	public List<TransferData> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<TransferData> transfers) {
		this.transfers = transfers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
