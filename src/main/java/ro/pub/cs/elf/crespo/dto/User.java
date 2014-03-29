package ro.pub.cs.elf.crespo.dto;

import java.net.Inet4Address;
import java.util.List;

/**
 * User POJO Object
 * 
 */
public class User {

	private String userName; // user name
	private List<File> sharedFiles; // user shared files
	private List<TransferData> transfers; // user transfers
	private int port; // user port
	private Inet4Address ipAddress; // user ip address

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

	public List<TransferData> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<TransferData> transfers) {
		this.transfers = transfers;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Inet4Address getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(Inet4Address ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return this.userName;
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
