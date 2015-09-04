package edu.nitrkl.graphics.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextReaderTest {

	public static void main(String[] args) throws IOException {
		// boolean success = false;
		File file = new File("settings");

		ArrayList<File> str = new ArrayList<File>();

		for (File aFile : file.listFiles()) 
			if(aFile.getName().matches("^.*[(.json)(.JSON)]$"))
				str.add(aFile);
		System.out.println(str);
		for(File aFile :str)
			if(aFile.getName().matches("^[(default)(Default)(DEFAULT)]{1,1}.+[(json)(JSON)]{1,1}$"))
				System.out.println(aFile);
		
	}
}
