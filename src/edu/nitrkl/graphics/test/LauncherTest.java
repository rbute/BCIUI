package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.ResizablePolygon;
import edu.nitrkl.graphics.components.ResizableTextJLabel;
import edu.nitrkl.ui.BCIUI;

public class LauncherTest {

	public static void main(String[] args) {

		BCIUI hyb = new BCIUI(false);
		hyb.result.setText("This is the Result Pane");
		hyb.choices.add(new ResizableTextJLabel("Choice Pane", 0.9f));

		JMenu files = new JMenu("Files");
		files.add(new JMenuItem("Open settings"));
		hyb.menuBar.add(files, 0);

		ResizablePolygon testPolygon = Factory.makeCenteredRectangle(
				0.9, 0.9);

		testPolygon.setForeground(Color.blue);

		hyb.choices.add((Component) testPolygon.getClone());
		hyb.choices.add(new JComponent() {
			private static final long serialVersionUID = 1L;
		});
		// hyb.choices.add((Component) testPolygon.getClone());
		hyb.choices.add((Component) testPolygon.getClone());

	}

}
