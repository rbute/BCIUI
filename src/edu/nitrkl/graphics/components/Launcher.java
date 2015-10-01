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
	 * 
	 * @--logging=[]
	 * 
	 */
	public static synchronized void main(String[] args)
			throws SecurityException, IOException {

		System.gc();

		Factory.getLogger().setLevel(Level.OFF);
		try {

			Factory.getLogger().info(" Starting Execution");
			// SessionManager mgr =
			new SessionManager(new JSONObject(new JSONTokener(new FileReader(
					Messages.getString("Launcher.DefaultSettingsFile")))));

			// mgr.buildUi(new JSONObject(new JSONTokener(new FileReader(
			// "settings/new.json"))));

		} catch (Exception e) {
			e.printStackTrace();
			Factory.getLogger().setLevel(Level.SEVERE);
			Factory.getLogger().throwing(Launcher.class.toString(),
					"public static void main()", e);
		}
	}

}
