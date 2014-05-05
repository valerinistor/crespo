package ro.pub.cs.elf.crespo.dto;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

/**
 * User POJO Object
 * 
 */
public class User {

	private String userName; // user name
	private List<UserFile> sharedFiles; // user shared files
	private List<TransferData> transfers; // user transfers
	private int port; // user port
	private Inet4Address ipAddress; // user ip address
	private String wsip; // web service ip address

	public User(String userName) {
		this.userName = userName;
		this.sharedFiles = new ArrayList<>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UserFile> getSharedFiles() {
		return sharedFiles;
	}

	public void setSharedFiles(List<UserFile> sharedFiles) {
		this.sharedFiles = sharedFiles;
	}

	public void addSharedFile(UserFile file) {
		this.sharedFiles.add(file);
	}

	public void removeSharedFile(UserFile file) {
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

	public String getWsip() {
		return wsip;
	}

	public void setWsip(String wsip) {
		this.wsip = wsip;
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
