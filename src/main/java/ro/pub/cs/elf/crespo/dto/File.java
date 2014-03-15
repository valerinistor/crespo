package ro.pub.cs.elf.crespo.dto;

public class File {

	private String fileName;

	public File(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return this.fileName;
	}
}
