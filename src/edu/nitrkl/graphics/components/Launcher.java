package edu.nitrkl.graphics.components;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Launcher {

	/**
	 * 
	 * @param args
	 * @throws SecurityException
	 * @throws IOException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @param --logging=[ ALL |SEVERE | WARNING | INFO | FINE | FINER | FINEST |
	 *        OFF ]
	 * @param --settings=<settingsFilePath>
	 * 
	 */
	public static synchronized void main(String[] args)
			throws SecurityException, IOException, IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException {

		System.gc();

		String settingsFile = Messages
				.getString("Launcher.DefaultSettingsFile");

		Factory.getLogger().setLevel(Level.OFF);

		for (String str : args) {
			if (str.matches("^(--logging=).*"))
				Factory.getLogger()
						.setLevel(
								((Level) Level.class.getField(
										str.split("^(--logging=)")[1]).get(
										Level.class)));

			if (str.matches("^(--settings=).*"))
				settingsFile = str.split("^(--settings=)")[1];
		}

		try {

			Factory.getLogger().info("Starting Execution");
			new SessionManager(new JSONObject(new JSONTokener(new FileReader(
					settingsFile))));


		} catch (Exception e) {
			e.printStackTrace();
			Factory.getLogger().setLevel(Level.SEVERE);
			Factory.getLogger().throwing(Launcher.class.toString(),
					"public static void main()", e);
		}
	}
}
