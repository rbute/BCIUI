package edu.nitrkl.graphics.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextReaderTest {

	public static void main(String[] args) throws IOException {
		// boolean success = false;
		File file = new File("settings");
		System.out.println(file.getAbsolutePath());

		ArrayList<String> str = new ArrayList<String>();

		for (File aFile : file.listFiles()) {
			if(aFile.getName().matches("^.*(.mat)$")){
				System.out.println(aFile.getName());
				str.add(aFile.getName());
			}
		}
		
//		for (File aFile: file.listFiles()){
//			System.out.println(aFile.getName());
//		}
			
		System.out.println(file.getPath());
	}
}
