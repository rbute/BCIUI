package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.nitrkl.graphics.components.BCIUI;
import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Singleton;
import edu.nitrkl.graphics.components.Polygon2;

public class SingletonConstructorTest {

	public static void main(String[] args) throws ClassNotFoundException,
			JSONException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		BCIUI ui = new BCIUI("", false);

		ui.result.setText(" ");

		// Singleton sample = new Singleton("A", new int[] { 1, 1 },
		// new Polygon2[] { Factory
		// .makeCenteredRectangle(0.5, 0.5) }, new Color[] {
		// Color.WHITE, new Color(25, 25, 25), Color.blue });

		
		
		
		Singleton sample = new Singleton(
				new JSONObject(
						"{\"symbol\":\"X\",\"elements\":[{\"Color\":{\"class\":\"java.awt.Color\"},\"text\":\"A\",\"position\":[1,1],\"class\":\"edu.nitrkl.graphics.components.ResizableTextJLabel\"},{\"class\":\"edu.nitrkl.graphics.components.Polygon2\",\"points\":[[0.25,0.25],[0.25,0],[0.75,0],[0.75,0.25],[1,0.25],[1,0.75],[0.75,0.75],[0.75,1],[0.25,1],[0.25,0.75],[0,0.75],[0,0.25]]},{\"class\":\"edu.nitrkl.graphics.components.Polygon2\",\"points\":[[0,0],[1,0],[1,1],[0,1]]}],\"index\":[1,1],\"class\":\"edu.nitrkl.graphics.components.Singleton\"}"));

		ui.choices.add(sample);

		Polygon2 poly = Factory.makeCenteredRectangle(0.5, 0.5);
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
