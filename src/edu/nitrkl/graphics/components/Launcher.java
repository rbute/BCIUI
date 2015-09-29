package edu.nitrkl.graphics.components;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Launcher {

	public static void main(String[] args) throws JSONException,
			FileNotFoundException {
		new SessionManager(new JSONObject(new JSONTokener(new FileReader(
				"settings/new.json"))));
	}

}
