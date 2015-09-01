package edu.nitrkl.graphics.test;

import java.awt.Color;

import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Polygon2;
import edu.nitrkl.graphics.components.Singleton;
import edu.nitrkl.ui.BCIUI;

public class SingletonMakerTest {

	public static void main(String[] args) {
		BCIUI ui = new BCIUI(true);

		ui.result.setText(" ");

		Singleton sample = new Singleton("A", new int[] { 1, 1 },
				new Polygon2[] { Factory
						.makeCenteredRectangle(0.5, 0.5) }, new Color[] {
						Color.WHITE, new Color(25, 25, 25), Color.blue });

		ui.choices.add(sample);

		// ui.choices.add(new FlasherSingleton("B", new int[] { 1, 1 },
		// new ResizablePolygon[] { ComponentFactory
		// .makeCenteredRectangle(0.5, 0.5) }, new Color[] {
		// Color.WHITE, new Color(25, 25, 25), Color.blue }));

		// ui.choices.add(new FlasherSingleton("C", new int[] { 1, 1 },
		// new ResizablePolygon[] { ComponentFactory
		// .makeCenteredRectangle(0.5, 0.5) }, new Color[] {
		// Color.WHITE, new Color(25, 25, 25), Color.blue }));

		// ui.choices.add(new FlasherSingleton("D", new int[] { 1, 1 },
		// new ResizablePolygon[] { ComponentFactory
		// .makeCenteredRectangle(1, 0.5) }, new Color[] {
		// Color.WHITE, new Color(25, 25, 25), Color.blue }));

	}

}
