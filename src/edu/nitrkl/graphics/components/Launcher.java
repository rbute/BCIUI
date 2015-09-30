package edu.nitrkl.graphics.components;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Launcher {

	public static synchronized void main(String[] args)
			throws SecurityException, IOException {

		System.gc();

		Factory.getLogger().setLevel(Level.ALL);
		try {

			Factory.getLogger().info(" Starting Execution");
			new SessionManager(new JSONObject(new JSONTokener(new FileReader(
					Messages.getString("Launcher.DefaultSettingsFile")))));

		} catch (Exception e) {
			Factory.getLogger().setLevel(Level.SEVERE);
			Factory.getLogger().throwing(Launcher.class.toString(),
					"public static void main()", e);
		}
	}

}
