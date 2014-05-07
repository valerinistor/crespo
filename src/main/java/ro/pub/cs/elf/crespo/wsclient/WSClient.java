package ro.pub.cs.elf.crespo.wsclient;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import ro.pub.cs.elf.crespo.dto.User;
import ro.pub.cs.elf.crespo.dto.UserFile;
import ro.pub.cs.elf.crespo.mediator.Mediator;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;

public class WSClient extends SwingWorker<Void, User> {

	private Mediator mediator;
	private String endpoint;
	private Service service;
	private static String SEP = "@";
	private int DELAY = 3000;

	public WSClient(Mediator mediator) {
		this.mediator = mediator;
		this.endpoint = "http://" + mediator.getMe().getWsip()
				+ ":8080/axis/services/UserService";
		this.service = new Service();

		Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("registerUser"));

			User me = mediator.getMe();
			StringBuilder message = new StringBuilder();
			message.append(me.getUserName() + SEP);
			message.append(me.getIpAddress().getHostAddress() + SEP);
			message.append(me.getPort() + SEP);
			for (UserFile uf : me.getSharedFiles()) {
				message.append(uf.getName());
				message.append(SEP);
			}
			call.invoke(new Object[] { message.toString() });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground() throws Exception {

		while (true) {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("retrieveUsers"));
			String raw = (String) call.invoke(new Object[] { mediator.getMe()
					.getUserName() });
			
			String[] rawUsers = raw.split(System.getProperty("line.separator"));
			for (String rawUser : rawUsers) {
				String[] userData = rawUser.split(SEP);
				User user = new User(userData[0]);
				user.setIpAddress((Inet4Address) Inet4Address
						.getByName(userData[1]));
				user.setPort(Integer.parseInt(userData[2]));

				List<UserFile> sharedFiles = new ArrayList<UserFile>();
				for (int idx = 3; idx < userData.length; idx++) {
					sharedFiles.add(new UserFile(user, userData[idx]));
				}
				user.setSharedFiles(sharedFiles);

				publish(user);
			}
			Thread.sleep(DELAY);
		}
	}

	/**
	 * update GUI with new users and files
	 */
	@Override
	protected void process(List<User> chunks) {
		User user = chunks.get(0);
		if (user != null) {
			this.mediator.addUser(user);
		}
	}
}