package ro.pub.cs.elf.crespo;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.BeforeClass;

import ro.pub.cs.elf.crespo.app.Crespo;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.mediator.Mediator;
import ro.pub.cs.elf.crespo.network.Network;

public class TestFileNotFound extends TestCase {

	private static User bugs;
	private static User daffy;
	private static User sam;

	@BeforeClass
	public void setUp() {
		bugs = Crespo.loadUser("bugs");
		Assert.assertNotNull(bugs);

		sam = Crespo.loadUser("sam");
		Assert.assertNotNull(sam);

		daffy = Crespo.loadUser("daffy");
		Assert.assertNotNull(daffy);
	}

	public void testNotFound() {
		Mediator samFakeMed = new Mediator();
		samFakeMed.registerMe(sam);

		Network network = new Network(samFakeMed);
		UserFile fileToReceive = new UserFile(bugs, ".bluff");

		TransferData td = new TransferData();
		td.setDestination(sam);
		td.setSource(bugs);
		td.setFile(fileToReceive);
		td.setProgress(0);
		td.setStatus(TransferStatus.SENDING);

		// send request for file
		network.sendFileRequest(td);

		Assert.assertEquals(new UserFile(".bluff").length(), 0);
	}
}