package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;

public class FlasherSingleton extends JComponent implements CloneableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9136644409217116184L;

	int[] index = null;

	protected FlasherSingleton(FlasherSingleton singleton) {
		this.index = new int[singleton.index.length];
		for (int i = 0; i < this.index.length; i++)
			this.index[i] = 0;

		for (Component clc : singleton.getComponents()){
			this.add(((CloneableComponent) clc).getClone());
		}
		this.setLayout(new ZStackingLayout());
	}

	public FlasherSingleton(int[] index) {
		for (int i : index)
			if (i <= 0)
				throw new IllegalArgumentException(
						"Index must be real positive nonzero integers");

		this.index = new int[index.length];
	}

	public FlasherSingleton(int[] index, Component[] components, Color[] colors) {
		this(index);
		if (components.length != colors.length)
			throw new IllegalArgumentException(
					"Number of colors must be Equal to Number Resizable Components.");
		this.setLayout(new ZStackingLayout());

		for (int i = 0; i < components.length; i++) {
			Component polygon = components[i];
			polygon.setForeground(colors[i]);
			super.add(polygon);
		}
	}

	public FlasherSingleton(String str, int[] index,
			ResizablePolygon[] polygons, Color[] colors) {
		this(index);
		if (polygons.length < colors.length - 2)
			throw new IllegalArgumentException(
					"Number of colors must be ' Number Resizable Polygons + 2 '");
		this.setLayout(new ZStackingLayout());

		ResizableTextJLabel label = null;

		label = new ResizableTextJLabel(str,0.8f);
		label.setForeground(colors[0]);
		label.setVisible(false);
		label.setOpaque(false);
		this.add(label);

		label = (ResizableTextJLabel) label.getClone();
		label.setForeground(colors[1]);
		label.setVisible(true);
		label.setOpaque(false);
		super.add(label);

		for (int i = 0; i < polygons.length; i++) {
			ResizablePolygon polygon = polygons[i];
			polygon.setForeground(colors[i + 2]);
			super.add(polygon);
		}
	}

	@Override
	public JComponent getClone() {
		return new FlasherSingleton(this);
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
