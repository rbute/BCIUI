package edu.nitrkl.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Flasher;
import edu.nitrkl.graphics.components.FlasherSingleton;
import edu.nitrkl.graphics.components.ResizableTextJLabel;

public class HardCoddedLauncher {

	public static void main(String[] args) {

		BCIUI hyb = new BCIUI(true);

		FlasherSingleton sample = new FlasherSingleton(new int[] { 1, 1 },
				new JComponent[] { new ResizableTextJLabel("+", 0.7f),
						new ResizableTextJLabel("+", 0.7f),
						Factory.makeCross(0.5, 0.5),
						Factory.makeCenteredRectangle(1, 1) }, new Color[] {
						Color.red, Color.black, Color.yellow, Color.white });

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
			flashers[i] = new Flasher((FlasherSingleton[]) boardOptions[i],
					200, 0.5, (byte) 2);
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

		JMenu files = new JMenu("Files");
		files.add(new JMenuItem("Open settings (To Be Implemented)"));
		hyb.menuBar.add(files, 0);

		/*
		 * 
		 * 
		 * 
		 */

		for (Flasher flasher : flashers)
			flasher.setFlash();

		// try {
		// TimeUnit.MILLISECOND.wait(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		// for (Flasher flasher : flashers)
		// flasher.setFlash((byte) 10);

		// try {
		// TimeUnit.MILLISECOND.wait(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		// for (Flasher flasher : flashers)
		// flasher.setFlash((int) 5000);

		// try {
		// TimeUnit.MILLISECOND.wait(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		// for (Flasher flasher : flashers){
		ArrayList<Flasher> temp = new ArrayList<Flasher>(flasherArray);
		flasherArray.get(0).setFlash(temp);
		// Collections.shuffle(flasherArray);
		while (!temp.isEmpty())
			;
		flasherArray.get(0).setFlash(new ArrayList<Flasher>(flasherArray));

		// }

		// try {
		// TimeUnit.MILLISECOND.wait(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		/*
		 * 
		 * 
		 * 
		 */

	}

}
