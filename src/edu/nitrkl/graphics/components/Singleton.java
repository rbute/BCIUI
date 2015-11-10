package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Invoke Action Command and send to action listeners.

public class Singleton extends JComponent implements CloneableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9136644409217116184L;

	int[] index = null;

	String name = "";
	ProcessBuilder processBuilder = null;

	public Singleton(int[] index) {
		for (int i : index)
			if (i <= 0)
				throw new IllegalArgumentException(
						"Index must be real positive nonzero integers");

		this.index = new int[index.length];
	}

	public Singleton(int[] index, Component[] components, Color[] colors) {
		this(index);
		if (components.length != colors.length)
			throw new IllegalArgumentException(
					"Number of colors must be Equal to Number Resizable Components.");
		this.setLayout(new OcculdingLayout());

		for (int i = 0; i < components.length; i++) {
			Component polygon = components[i];
			polygon.setForeground(colors[i]);
			super.add(polygon);
		}
	}

	public Singleton(JSONObject singleton) throws ClassNotFoundException,
			JSONException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {
		// TODO Auto-generated constructor stub
		this.name = singleton.getString("symbol");
		JSONArray arr = singleton.getJSONArray("index");
		this.index = new int[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			this.index[i] = arr.getInt(i);
		}
		arr = singleton.getJSONArray("elements");

		this.setLayout(new OcculdingLayout());

		for (int i = 0; i < arr.length(); i++) {
			JSONObject jObj = arr.getJSONObject(i);
			// System.out.println(jObj.get("class"));

			@SuppressWarnings("unchecked")
			Class<? extends JComponent> cls = (Class<? extends JComponent>) Class
					.forName(jObj.get("class").toString());

			Constructor<? extends JComponent> cons = cls
					.getConstructor(new Class[] { JSONObject.class });

			JComponent comp = cons.newInstance(jObj);

			comp.setVisible(true);

			// this.add(((Class<? extends JComponent>) Class.forName(arr
			// .getJSONObject(i).getString("class").toString()))
			// .getConstructor(new Class[] { JSONObject.class })
			// .newInstance(arr.getJSONObject(i)));

			this.add(comp);
		}

		if (singleton.has("process"))
			this.processBuilder = new ProcessBuilder(
					singleton.getString("process"));
	}

	protected Singleton(Singleton singleton) {
		this.index = new int[singleton.index.length];
		for (int i = 0; i < this.index.length; i++)
			this.index[i] = 0;

		for (Component clc : singleton.getComponents()) {
			this.add(((CloneableComponent) clc).getClone());
		}
		this.setLayout(new OcculdingLayout());
	}

	public Singleton(String str, int[] index, JComponent[] jComponents,
			Color[] colors) {
		this(index);
		if (jComponents.length > colors.length)
			throw new IllegalArgumentException(
					"Number of colors must be greater than or equal to the number of components");
		this.setLayout(new OcculdingLayout());

		for (int i = 0; i < jComponents.length; i++) {
			JComponent component = jComponents[i];
			component.setForeground(colors[i]);
			if (component instanceof JLabel)
				((JLabel) component).setText(str);
			super.add(component);
		}
	}

	public void hideSublayers() {
		for (int i = 1; i < super.getComponents().length; i++)
			super.getComponent(i).setVisible(false);
	}

	@Override
	public Singleton getClone() {
		return new Singleton(this);
	}

	public int[] getIndex() {
		return index;
	}

	public synchronized ProcessBuilder getProcessBuilder() {
		return processBuilder;
	}

	public void paintImmediately() {
		super.paintImmediately(this.getBounds());
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		for (Component component : getComponents()) {
			component.setBounds(x, y, width, height);
		}
	}

	public void setIndex(int[] inputIndex) {
		if (inputIndex.length != index.length)
			throw new IllegalArgumentException("Input Index Dimention("
					+ index.length
					+ ") doesn't match Initialization Dimension("
					+ inputIndex.length + ")");
		else
			this.index = inputIndex;
	}

	public synchronized void setProcessBuilder(ProcessBuilder processBuilder) {
		this.processBuilder = processBuilder;
	}

	@Override
	public void setSize(int width, int height) {
		for (Component component : getComponents()) {
			component.setSize(width, height);
		}
		super.setSize(width, height);
	}
}
