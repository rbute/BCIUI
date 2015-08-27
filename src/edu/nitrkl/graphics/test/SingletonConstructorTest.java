package edu.nitrkl.graphics.test;

import java.awt.Color;

import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Singleton;
import edu.nitrkl.graphics.components.Polygon2;
import edu.nitrkl.ui.BCIUI;

public class SingletonConstructorTest {

	public static void main(String[] args) {
		BCIUI ui = new BCIUI(false);

		ui.result.setText(" ");

		Singleton sample = new Singleton("A", new int[] { 1, 1 },
				new Polygon2[] { Factory
						.makeCenteredRectangle(0.5, 0.5) }, new Color[] {
						Color.WHITE, new Color(25, 25, 25), Color.blue });

		ui.choices.add(sample);

		Polygon2 poly = Factory
				.makeCenteredRectangle(0.5, 0.5);
		poly.setForeground(Color.blue);
		sample.add(poly);
		// ui.choices.add(sample.clone());

		// ui.choices.add();

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
