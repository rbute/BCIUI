package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

// TODO: Invoke Action Command and send to action listeners.

public class Singleton extends JComponent implements CloneableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9136644409217116184L;

	int[] index = null;
	String actionCommand = "";
	String name = "";
	String option = "";

	protected Singleton(Singleton singleton) {
		this.index = new int[singleton.index.length];
		for (int i = 0; i < this.index.length; i++)
			this.index[i] = 0;

		for (Component clc : singleton.getComponents()) {
			this.add(((CloneableComponent) clc).getClone());
		}
		this.setLayout(new OcculdingLayout());
	}

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

	public Singleton(String str, int[] index, JComponent[] jComponents,
			Color[] colors) {
		this(index);
		if (jComponents.length < colors.length)
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

	public Singleton(JSONObject singleton) {
		// TODO Auto-generated constructor stub
		this.name = singleton.getString("symbol");
		JSONArray arr = singleton.getJSONArray("index");
		this.index = new int[arr.length()];
		for (int i = 0; i < arr.length(); i++) {
			this.index[i] = arr.getInt(i);
		}
		arr = singleton.getJSONArray("elements");
	}

	@Override
	public Singleton getClone() {
		return new Singleton(this);
	}

	public int[] getIndex() {
		return index;
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

	@Override
	public void setSize(int width, int height) {
		for (Component component : getComponents()) {
			component.setSize(width, height);
		}
		super.setSize(width, height);
	}
}
