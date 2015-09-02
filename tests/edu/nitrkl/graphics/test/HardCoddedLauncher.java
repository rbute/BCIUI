package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JComponent;

import edu.nitrkl.graphics.components.BCIUI;
import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Flasher;
import edu.nitrkl.graphics.components.ResizableTextJLabel;
import edu.nitrkl.graphics.components.Singleton;

public class HardCoddedLauncher {

	public static void main(String[] args) {

		BCIUI hyb = new BCIUI(true);

		Singleton sample = new Singleton(new int[] { 1, 1 }, new JComponent[] {
				new ResizableTextJLabel("+", 0.7f),
				// new ResizableTextJLabel("+", 0.7f),
				Factory.makeCross(0.5, 0.5),
				Factory.makeCenteredRectangle(1, 1) }, new Color[] {// Color.red,
				Color.red, Color.yellow, Color.white });

		String[][] options = { { "A", "B", "C", "D", "E", "F" },
				{ "G", "H", "I", "J", "K", "L", },
				{ "M", "N", "O", "P", "Q", "R" },
				{ "S", "T", "U", "V", "W", "X" },
				{ "Y", "Z", "0", "1", "2", "3" },
				{ "4", "5", "6", "7", "8", "9" } };

		JComponent[][] boardOptions = Factory.makeBoard(options, sample);

		Flasher[] flashers = new Flasher[boardOptions.length];

		ArrayList<Flasher> flasherArray = new ArrayList<Flasher>();

		for (int i = 0; i < boardOptions.length; i++) {
			flashers[i] = new Flasher((Singleton[]) boardOptions[i], 200, 0.5,
					(byte) 1);
			flasherArray.add(flashers[i]);
		}

		hyb.result.setText("Result");

		hyb.choices.setLayout(new GridLayout(boardOptions.length,
				boardOptions[0].length, 40, 40));

		for (JComponent[] comp : boardOptions) {
			for (JComponent aComp : comp) {
				hyb.choices.add(aComp);
			}
		}

		for (Flasher flasher : flashers)
			flasher.setFlash();

		ArrayList<Flasher> shuffledArray = new ArrayList<Flasher>(flasherArray);
		
		for (int i = 0; i < 5; i++) {
			Collections.shuffle(shuffledArray);
			ArrayList<Flasher> temp = new ArrayList<Flasher>(shuffledArray);
			flasherArray.get(0).setFlash(temp);
			while (!temp.isEmpty())
				;
		}
	}
}
