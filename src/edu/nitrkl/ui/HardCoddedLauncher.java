package edu.nitrkl.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.nitrkl.graphics.components.ComponentFactory;
import edu.nitrkl.graphics.components.FlasherSingleton;
import edu.nitrkl.graphics.components.ResizableTextJLabel;

public class HardCoddedLauncher {

	public static void main(String[] args) {

		BCIUI hyb = new BCIUI();

		FlasherSingleton sample = new FlasherSingleton(new int[] { 1, 1 },
				new JComponent[] { new ResizableTextJLabel("+", 0.7f),
						ComponentFactory.makeCross(0.5, 0.5),
						ComponentFactory.makeCenteredRectangle(1, 0.5) }, new Color[] {
						Color.red, Color.WHITE, Color.yellow });
		
		String[][] options = { { "A", "B", "C", "D", "E", "F" },
				{ "G", "H", "I", "J", "K", "L", },
				{ "M", "N", "O", "P", "Q", "R" },
				{ "S", "T", "U", "V", "W", "X" },
				{ "Y", "Z", "0", "1", "2", "3" },
				{ "4", "5", "6", "7", "8", "9" } };
		
		JComponent[][] boardOptions = ComponentFactory.makeBoard(options, sample);;
		
		hyb.result.setText("Result");
		// hyb.choices.add(new ResizableTextJLabel("Choice Pane", 0.9f));
		hyb.choices.add(sample);
		
		

		hyb.choices.setLayout(new GridLayout(boardOptions.length,
				boardOptions[0].length));

		for (JComponent[] comp : boardOptions) {
			for (JComponent aComp : comp) {
				hyb.choices.add(aComp);
			}
		}

		JMenu files = new JMenu("Files");
		files.add(new JMenuItem("Open settings (Yet To Be Implemented)"));
		hyb.menuBar.add(files, 0);

	}

}
