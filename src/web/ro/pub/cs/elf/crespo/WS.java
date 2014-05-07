package ro.pub.cs.elf.crespo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WS {

	private static File db = new File("database.txt");

	public void registerUser(String user) throws IOException {
		if (!db.exists()) {
			db.createNewFile();
		}
		FileWriter fw = new FileWriter(db.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(user);
		bw.close();
	}

	public void unregisterUser(String user) throws IOException {
		File tempFile = new File(db.getAbsolutePath() + "temp");

		BufferedReader reader = new BufferedReader(new FileReader(db));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			if (currentLine.split("|")[0].equals(user))
				continue;
			writer.write(currentLine);
		}

		reader.close();
		writer.close();
		tempFile.renameTo(db);
	}

	public String retrieveUsers() throws IOException {
		return new String(Files.readAllBytes(Paths.get(db.getAbsolutePath())),
				StandardCharsets.UTF_8);
	}
}
