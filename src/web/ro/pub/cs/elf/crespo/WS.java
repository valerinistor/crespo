package ro.pub.cs.elf.crespo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WS {

	private static Map<String, String> db = new HashMap<String, String>();
	private static final String SEP = "@";
	
	public void registerUser(String userData) throws IOException {
		String userName = userData.substring(0, userData.indexOf(SEP));
		String data = userData.substring(userData.indexOf(SEP) + 1);
		db.put(userName, data);
	}

	public void unregisterUser(String user) throws IOException {
		db.remove(user);
	}

	public String retrieveUsers(String solicitant) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e : db.entrySet()) {
			if (!e.getKey().equals(solicitant)) {
				sb.append(e.getKey() + SEP + e.getValue());
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}
}
