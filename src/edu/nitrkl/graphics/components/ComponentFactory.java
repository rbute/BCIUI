package edu.nitrkl.graphics.components;

import java.awt.Polygon;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class ComponentFactory {

	public static JComponent[][] makeBoard(String[][] options,
			FlasherSingleton singleton) {
		JComponent[][] singletons = new FlasherSingleton[options.length][options[0].length];
		for (int i = 0; i < options.length; i++) {
			for (int j = 0; j < options[0].length; j++) {
				if (options[i][j] == null) {
					// FIXME: Error Thrown
					singletons[i][j] = new JComponent() {
						private static final long serialVersionUID = 7155706997007627766L;
					};

				} else {
					singletons[i][j] = singleton.getClone();
					
					//TODO: Set Text if extends JLabel
					
					
					((JLabel) singletons[i][j].getComponent(0))
							.setText(options[i][j]);
					((JLabel) singletons[i][j].getComponent(1))
							.setText(options[i][j]);
					((FlasherSingleton) singletons[i][j]).setIndex(new int[] {
							i, j });
				}
			}
		}
		return singletons;
	}

	public static ResizablePolygon makeCenteredRectangle(double width,
			double height) {
		ResizablePolygon polygon = new ResizablePolygon();
		polygon.addPoint(0.5 * (1 - width), 0.5 * (1 - height));
		polygon.addPoint(0.5 * (1 - width), 0.5 * (1 + height));
		polygon.addPoint(0.5 * (1 + width), 0.5 * (1 + height));
		polygon.addPoint(0.5 * (1 + width), 0.5 * (1 - height));
		return polygon;
	}

	public static ResizablePolygon makeCross(double horizontalPatchWidth,
			double verticalPathcWidth) {
		return ComponentFactory.makeCross(horizontalPatchWidth,
				verticalPathcWidth, 1000, 1000);
	}

	public static ResizablePolygon makeCross(double horizontalPatchWidth,
			double verticalPathcWidth, int width, int height) {
		Integer[] xMarkers = { 0,
				0 + (int) ((1 - horizontalPatchWidth) * 0.5 * (width)),
				0 + (int) ((1 + horizontalPatchWidth) * 0.5 * (width)),
				0 + (width) };
		Integer[] yMarkers = { 0,
				0 + (int) ((1 - verticalPathcWidth) * 0.5 * (height)),
				0 + (int) ((1 + verticalPathcWidth) * 0.5 * (height)),
				0 + (height) };

		Polygon p = new Polygon();
		p.addPoint(xMarkers[0], yMarkers[1]);
		p.addPoint(xMarkers[1], yMarkers[1]);
		p.addPoint(xMarkers[1], yMarkers[0]);
		p.addPoint(xMarkers[2], yMarkers[0]);
		p.addPoint(xMarkers[2], yMarkers[1]);
		p.addPoint(xMarkers[3], yMarkers[1]);
		p.addPoint(xMarkers[3], yMarkers[2]);
		p.addPoint(xMarkers[2], yMarkers[2]);
		p.addPoint(xMarkers[2], yMarkers[3]);
		p.addPoint(xMarkers[1], yMarkers[3]);
		p.addPoint(xMarkers[1], yMarkers[2]);
		p.addPoint(xMarkers[0], yMarkers[2]);

		p.getBounds().setBounds(0, 0, 1, 1);
		return new ResizablePolygon(p);
	}

	public static JLabel[] makeJLabels(String[] inputStrings) {
		JLabel[] temp = new JLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i] = new JLabel();
			temp[i].setText(inputStrings[i]);
			temp[i].setOpaque(true);
		}
		return temp;
	}

	public static JLabel[][] makeJLabels(String[][] inputStrings) {
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

	public static ResizableTextJLabel[] makeResizingTextJLabels(
			String[] inputStrings) {
		ResizableTextJLabel[] temp = new ResizableTextJLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i] = new ResizableTextJLabel();
			temp[i].setText(inputStrings[i]);
			temp[i].setOpaque(true);
		}
		return temp;
	}

	public static ResizableTextJLabel[][] makeResizingTextJLabels(
			String[][] inputStrings) {
		ResizableTextJLabel[][] temp = new ResizableTextJLabel[inputStrings[0].length][inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new ResizableTextJLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}

	public static SquareJLabel[] makeSquareJLabels(String[] inputStrings) {
		SquareJLabel[] temp = new SquareJLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i].setText(inputStrings[i]);
		}
		return temp;
	}

	public static SquareJLabel[][] makeSquareJLabels(String[][] inputStrings) {
		SquareJLabel[][] temp = new SquareJLabel[inputStrings[0].length][inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new SquareJLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}

}
