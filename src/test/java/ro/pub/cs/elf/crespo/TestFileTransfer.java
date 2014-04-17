package ro.pub.cs.elf.crespo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ro.pub.cs.elf.crespo.app.Crespo;
import ro.pub.cs.elf.crespo.dto.TransferData;
import ro.pub.cs.elf.crespo.dto.TransferData.TransferStatus;
import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.mediator.Mediator;
import ro.pub.cs.elf.crespo.network.Network;

public class TestFileTransfer extends TestCase {

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

	@Test
	public void testTransfer() {
		Mediator samFakeMed = new Mediator();
		samFakeMed.registerMe(sam);

		Network network = new Network(samFakeMed);
		UserFile fileToReceive = bugs.getSharedFiles().get(0);

		TransferData td = new TransferData();
		td.setDestination(sam);
		td.setSource(bugs);
		td.setFile(fileToReceive);
		td.setProgress(0);
		td.setStatus(TransferStatus.SENDING);

		// send request for file
		network.sendFileRequest(td);

		try {
			// wait for network to download the file
			Thread.sleep(5);

			UserFile receivedFile = new UserFile(bugs.getSharedFiles().get(0).getName());

			// check that original and downloaded file have not the same path
			Assert.assertNotEquals(
					fileToReceive.getCanonicalPath(),
					receivedFile.getCanonicalPath());

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] expectedBytes = md.digest(Files.readAllBytes(Paths.get(fileToReceive.getAbsolutePath())));
			byte[] actualBytes = md.digest(Files.readAllBytes(Paths.get(receivedFile.getAbsolutePath())));

			// check md5sum of original and downloaded file
			Assert.assertArrayEquals(expectedBytes, actualBytes);
		} catch (NoSuchAlgorithmException nsae) {
			fail();
		} catch (IOException ioe) {
			fail();
		} catch (InterruptedException ie) {
			fail();
		}
	}

	@Test
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