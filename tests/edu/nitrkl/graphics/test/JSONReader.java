package edu.nitrkl.graphics.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONReader {

	private static FileReader fr;

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		fr = new FileReader("settings/default.json");
		JSONTokener tokener = new JSONTokener(fr);

		JSONObject jsonObject = new JSONObject(tokener);

		System.out.println(jsonObject);
	}

}
