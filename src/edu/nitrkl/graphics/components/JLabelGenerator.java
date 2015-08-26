package edu.nitrkl.graphics.components;

import javax.swing.JLabel;

public class JLabelGenerator {

	public static SquareJLabel[] generateSquareJLabels(String[] inputStrings) {
		SquareJLabel[] temp = new SquareJLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i].setText(inputStrings[i]);
		}
		return temp;
	}

	public static SquareJLabel[][] generateSquareJLabels(String[][] inputStrings) {
		SquareJLabel[][] temp = new SquareJLabel[inputStrings[0].length][inputStrings.length];
		// System.out.println("inputStrings.length "+inputStrings.length);
		// System.out.println("inputStrings[0].length "+inputStrings[0].length);
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new SquareJLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}

	public static JLabel[] generateJLabels(String[] inputStrings) {
		JLabel[] temp = new JLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i] = new JLabel();
			temp[i].setText(inputStrings[i]);
			temp[i].setOpaque(true);
		}
		return temp;
	}

	public static JLabel[][] generateJLabels(String[][] inputStrings) {
		JLabel[][] temp = new JLabel[inputStrings[0].length][inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new JLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}

	public static ResizableTextJLabel[] generateResizingTextJLabels(
			String[] inputStrings) {
		ResizableTextJLabel[] temp = new ResizableTextJLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i] = new ResizableTextJLabel();
			temp[i].setText(inputStrings[i]);
			temp[i].setOpaque(true);
		}
		return temp;
	}

	public static ResizableTextJLabel[][] generateResizingTextJLabels(
			String[][] inputStrings) {
		ResizableTextJLabel[][] temp = new ResizableTextJLabel[inputStrings[0].length][inputStrings.length];
		// System.out.println("inputStrings.length "+inputStrings.length);
		// System.out.println("inputStrings[0].length "+inputStrings[0].length);
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new ResizableTextJLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}
}
