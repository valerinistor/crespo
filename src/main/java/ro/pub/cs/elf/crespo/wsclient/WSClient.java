package ro.pub.cs.elf.crespo.wsclient;

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
	private static String SEP = "|";
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

			StringBuilder message = new StringBuilder();
			message.append(mediator.getMe().getUserName());
			message.append(SEP);
			for (UserFile uf : mediator.getMe().getSharedFiles()) {
				message.append(uf.getName());
				message.append(SEP);
			}
			call.invoke(new Object[]{message.toString()});
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
			String ret = (String) call.invoke(new Object[] {});
			System.out.println("#######\n" + ret);
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
