package ro.pub.cs.elf.crespo.dto;

/**
 * Transfer row data
 *
 */
public class TransferData {

	/* Transfer States */
	public enum TransferStatus {
		SENDING("Sending..."),
		RECEIVING("Receiving..."),
		COMPLETED("Completed");

		private final String name;

		private TransferStatus(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private User source;
	private User destination;
	private File file;
	private int progress;
	private TransferStatus status;

	public User getSource() {
		return source;
	}

	public void setSource(User source) {
		this.source = source;
	}

	public User getDestination() {
		return destination;
	}

	public void setDestination(User destination) {
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
		if (progress == 100) {
			setStatus(TransferStatus.COMPLETED);
		}
	}

	public TransferStatus getStatus() {
		return status;
	}

	public void setStatus(TransferStatus status) {
		this.status = status;
	}
}
