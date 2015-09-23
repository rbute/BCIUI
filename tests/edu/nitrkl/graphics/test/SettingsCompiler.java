package edu.nitrkl.graphics.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SettingsCompiler {

	File file = null;
	String data = "";

	public SettingsCompiler(String filePath) throws IOException {
		// TODO Auto-generated constructor stub
		file = new File(filePath);
		if (!file.isFile())
			throw new IllegalArgumentException(
					"Either the file is a Directory or Non Existent");
		if (!file.getAbsoluteFile().getAbsoluteFile().toString()
				.matches("(.*\\.settings)"))
			throw new IllegalArgumentException("Not a valid file type");
		System.out.println("Settings file found.");

		FileReader fr = new FileReader(file);

	}

	public static void main(String[] args) throws IOException {
		File file = new File("./settings/test.settings");
		System.out.println(file.getName());
		// for (File aFile : file.listFiles()) {
		// System.out.println(aFile.getName());
		// }
		SettingsCompiler comp = new SettingsCompiler("./settings/test.settings");
	}
}
