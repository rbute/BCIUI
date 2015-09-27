package edu.nitrkl.graphics.test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import edu.nitrkl.graphics.components.BCIUI;
import edu.nitrkl.graphics.components.Singleton;

public class SingletonMakerTest {

	public static void main(String[] args) throws ClassNotFoundException,
			JSONException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {
		BCIUI ui = new BCIUI("", true);

		ui.result.setText(" ");

		// Singleton sample = new Singleton("A", new int[] { 1, 1 },
		// new JComponent[] { new ResizableTextJLabel("A", 0.6f),
		// Factory.makeCenteredRectangle(0.8, 0.8) }, new Color[] {
		// Color.WHITE, Color.blue, Color.blue, Color.red });

		 JSONObject jsObj = new JSONObject(new JSONTokener(new FileReader(
		 "settings/new.json")));
		
		 Singleton sample = new
		 Singleton(jsObj.getJSONObject("singletonmodel"));

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
		// new ResizablePolygon[] { ComponentFactorynew
		// .makeCenteredRectangle(1, 0.5) }, new Color[] {
		// Color.WHITE, new Color(25, 25, 25), Color.blue }));

	}

}
