package ro.pub.cs.elf.crespo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WSServer {

	private static Map<String, String> db = new ConcurrentHashMap<>();
	private static Map<String, Long> lastAccess = new ConcurrentHashMap<>();
	private static final String SEP = "@";
	private static boolean wiping = false;

	/**
	 * Register an user to server
	 * @param userData user information
	 */
	public static void registerUser(String userData) {
		String userName = userData.substring(0, userData.indexOf(SEP));
		String data = userData.substring(userData.indexOf(SEP) + 1);

		db.put(userName, data);
		lastAccess.put(userName, System.currentTimeMillis());

		if (!wiping) {
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					for (Map.Entry<String, Long> e : lastAccess.entrySet()) {

						Long curr = System.currentTimeMillis();
						if (curr - e.getValue() > 11e3) {
							db.remove(e.getKey());
							lastAccess.remove(e.getKey());
						}
					}
				}
			}, 5, 5, TimeUnit.SECONDS);
			wiping = true;
		}
	}

	/**
	 * Retrieve all connected users
	 * @param solicitant solicitant user
	 * @return
	 */
	public static String retrieveUsers(String solicitant) {
		lastAccess.put(solicitant, System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> e : db.entrySet()) {
			sb.append(e.getKey() + SEP + e.getValue());
			sb.append("~");
		}
		return sb.toString().trim();
	}
}