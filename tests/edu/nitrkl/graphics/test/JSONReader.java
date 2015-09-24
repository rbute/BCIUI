package edu.nitrkl.graphics.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONReader {

	private static FileReader fr;

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		fr = new FileReader("settings/new.json");
		JSONTokener tokener = new JSONTokener(fr);

		JSONObject jsonObject = new JSONObject(tokener);

		System.out.println(jsonObject);
		// System.out.println(jsonObject.get("keys"));

		JSONArray arr = jsonObject.getJSONArray("keys");

		JSONArray arr2 = (JSONArray) arr.get(0);

		System.out.println("Array Element: " + arr2.get(0));
		// JSONObject uioptions = new
		// JSONObject(jsonObject.get("keys").toString());

		// System.out.println(uioptions.get("title"));
		// boolean undecorate = uioptions.getBoolean("undecorate");
		// System.out.println(undecorate);
	}

}
