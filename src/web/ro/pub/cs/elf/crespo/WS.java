package ro.pub.cs.elf.crespo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WS {

	private static Map<String, String> db = new ConcurrentHashMap<>();
	private static Map<String, Long> lastAccess = new ConcurrentHashMap<>();
	private static final String SEP = "@";
	private static boolean wiping = false;

	public static void registerUser(String userData) throws IOException {

		String userName = userData.substring(0, userData.indexOf(SEP));
		String data = userData.substring(userData.indexOf(SEP) + 1);

		File file = new File("/tmp/crespo.log");

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write("register user: " + userName + " : " + data + "\n");
		bw.close();
		db.put(userName, data);
		lastAccess.put(userName, System.currentTimeMillis());

		if (!wiping) {
			ScheduledExecutorService scheduler = Executors
			        .newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					File file = new File("/tmp/crespo.log");

					try {
						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file, true);
						BufferedWriter bw = new BufferedWriter(fw);

						for (Map.Entry<String, Long> e : lastAccess.entrySet()) {
							Long curr = System.currentTimeMillis();
							bw.write(e.getKey() + " access diff: " + (curr - e.getValue()) + "\n");

							if (curr - e.getValue() > 11e3) {
								db.remove(e.getKey());
								lastAccess.remove(e.getKey());
								bw.write("user " + e.getKey() + " offline\n");
							}
						}
						bw.close();
					} catch (IOException e1) {
					}
				}
			}, 5, 5, TimeUnit.SECONDS);
			wiping = true;
		}
	}

	public static String retrieveUsers(String solicitant) throws IOException {
		lastAccess.put(solicitant, System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> e : db.entrySet()) {
			sb.append(e.getKey() + SEP + e.getValue());
			sb.append("~");
		}
		return sb.toString().trim();
	}
}