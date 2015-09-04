package edu.nitrkl.graphics.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

public class OcculdingLayout implements LayoutManager {

	HashMap<String, Component> components = new HashMap<String, Component>();

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		components.put(arg0, arg1);
	}

	@Override
	public void layoutContainer(Container arg0) {
		for (Component aComponent : arg0.getComponents()) {
			aComponent.setBounds(0, 0, arg0.getBounds().width,
					arg0.getBounds().height);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return null;
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		components.remove(arg0);
	}

}
