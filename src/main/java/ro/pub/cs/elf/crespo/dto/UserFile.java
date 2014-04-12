package ro.pub.cs.elf.crespo.dto;

import java.io.File;

/**
 * File POJO Object
 *
 */
public class UserFile extends File {

	private static final long serialVersionUID = 5158261789122094296L;
	private User owner; // User that own the file

	public UserFile(String pathname) {
		super(pathname);
	}

	public UserFile(User owner, String pathname) {
		super(pathname);
		setOwner(owner);
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFile other = (UserFile) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}
}
