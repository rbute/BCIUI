package edu.nitrkl.graphics.test;

import java.util.ArrayList;

public class PolygonInputProcessing {

	public static void main(String[] args) {
		String input = "";

		for (String str : args)
			input += str+"  ";

		System.out.println(input);
		input = input.replaceAll(" ", "");
		input = input.replaceAll("\\[\\[", "[");
		input = input.replaceAll("\\]\\]", "]");
		System.out.println(input);
		String[] values = input.split("(\\],\\[)");
		ArrayList<String[]> points = new ArrayList<String[]>();

		for (String str : values) {
			str = str.replaceAll("\\[", "");
			str = str.replaceAll("\\]", "");
			System.out.println(str);
			points.add(str.split("(,)"));
		}

		for (String[] strings : points) {
			for (String string : strings) {
				 System.out.print((new Double("  "+string+" ")) + " ");
				System.out.println(string);
			}
		}

		System.out.println((new Double("0.30")));
	}
}
