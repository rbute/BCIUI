package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;

import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Singleton;
import edu.nitrkl.ui.BCIUI;

public class PalletMakerFactoryTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BCIUI ui = new BCIUI(false);

		ui.result.setText("Result Pane");

		Singleton sample = new Singleton("X", new int[] { 1, 1 },
				new JComponent[] { Factory
						.makeCenteredRectangle(0.8, 0.8) }, new Color[] {
						Color.WHITE, new Color(25, 25, 25), Color.blue });

		String[][] options = { { "A", "B", "C", "D", "E", "F" },
				{ "G", "H", "I", "J", "K", "L", },
				{ "M", "N", "O", "P", "Q", "R" },
				{ "S", "T", "U", "V", "W", "X" },
				{ "Y", "Z", "0", "1", "2", "3" },
				{ "4", "5", "6", "7", "8", "9" } };

		JComponent[][] components = Factory.makeBoard(options, sample);

		ui.choices.setLayout(new GridLayout(components.length,
				components[0].length));

		for (JComponent[] comp : components) {
			for (JComponent aComp : comp) {
				ui.choices.add(aComp);
			}
		}
//		ui.choices.add(sample);
	}
}
