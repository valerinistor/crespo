package ro.pub.cs.elf.crespo.dto;

public class TransferData {

	public enum TransferStatus {
		SENDING("Sending..."), RECEIVING("Receiving..."), COMPLETED("Completed");

		private final String name;

		private TransferStatus(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}
	private String source;
	private String destination;
	private File file;
	private int progress;
	private TransferStatus status;

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public TransferStatus getStatus() {
		return status;
	}
	public void setStatus(TransferStatus status) {
		this.status = status;
	}
}
