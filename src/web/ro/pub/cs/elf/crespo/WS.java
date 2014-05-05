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

	private static String db = "database.txt";

	public String registerUser(String... user) throws IOException {
		File file = new File(db);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for (String s : user) {
			bw.write(s + "|");
		}
		bw.write("\n");
		bw.close();
		return "mumu";
	}

	public void unregisterUser(String user) throws IOException {
		File inputFile = new File(db);
		File tempFile = new File(db + "temp");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			if (currentLine.split("|")[0].equals(user))
				continue;
			writer.write(currentLine);
		}

		reader.close();
		writer.close();
		tempFile.renameTo(inputFile);
	}

	public String retrieveUsers() throws IOException {
		return new String(Files.readAllBytes(Paths.get(db)),
				StandardCharsets.UTF_8);
	}
}