package edu.nitrkl.graphics.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

public class JSONReader {

	public static void main(String[] args) throws FileNotFoundException, IOException {
//		File json = new File("settings/default.json");
//		System.out.println(json+" exists: "+json.exists());
//		JSONReader
		
		char[] cbuf=new char[0];
		FileReader fr = new FileReader("settings/default.json");
		fr.read(cbuf);
		String jsonData = new String(cbuf);
		
		JSONObject jsonObj = new JSONObject();
		
		System.out.println(jsonObj.get("Class"));
	}

}
