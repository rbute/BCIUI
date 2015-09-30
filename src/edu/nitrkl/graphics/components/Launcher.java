package edu.nitrkl.graphics.components;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Launcher {

	public static synchronized void main(String[] args) throws JSONException,
			FileNotFoundException, InterruptedException,
			ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		System.gc();
		new SessionManager(new JSONObject(new JSONTokener(new FileReader(
				"settings/new.json"))));
		System.gc();
		// synchronized (TimeUnit.MINUTES) {
		// TimeUnit.MINUTES.wait();
		// }
		// System.out.println("Session ended");
	}

}
