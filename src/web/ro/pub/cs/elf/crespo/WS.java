package ro.pub.cs.elf.crespo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WS {

	private static Map<String, String> db = new HashMap<String, String>();
	private static Map<String, Long> lastAccess = new HashMap<String, Long>();
	private static final String SEP = "@";
	private static boolean wiping = false;

	public void registerUser(String userData) throws IOException {
		String userName = userData.substring(0, userData.indexOf(SEP));
		String data = userData.substring(userData.indexOf(SEP) + 1);
		db.put(userName, data);
		lastAccess.put(userName, System.currentTimeMillis());

		if (!wiping) {
			ScheduledExecutorService scheduler = Executors
					.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					for (Map.Entry<String, Long> e : lastAccess.entrySet()) {
						Long curr = System.currentTimeMillis();
						if (curr - e.getValue() > 15e3) {
							db.remove(e.getKey());
							lastAccess.remove(e.getKey());
						}
					}
				}
			}, 5, 5, TimeUnit.SECONDS);
			wiping = true;
		}
	}

	public String retrieveUsers(String solicitant) throws IOException {
		lastAccess.put(solicitant, System.currentTimeMillis());
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
