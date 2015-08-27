package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;

public abstract class FlasherTemplate extends Thread implements EventListener {

	protected boolean flashing = true;
	protected ArrayList<JComponent> groupList = new ArrayList<JComponent>();
	protected String groupName = "";
	protected ReentrantLock lock = new ReentrantLock();

	protected double dutyCycle = 0.5;
	protected int timePeriod = 76;
	// TODO: Event Based Handling
	protected String stopCommand = "";

	public void addElement(JComponent group) {
		groupList.add(group);
	}

	public void addElements(ArrayList<JComponent> group) {
		groupList.addAll(group);
	}

	public void addElements(JComponent[] group) {
		for (JComponent aLabel : group)
			groupList.add(aLabel);
	}

	public void delElements(ArrayList<JComponent> group) {
		for (JComponent aLabel : group)
			groupList.remove(aLabel);
	}

	public void delElements(JComponent group) {
		groupList.remove(group);
	}

	public double getDutyCycle() {
		return this.dutyCycle;
	}

	public ArrayList<JComponent> getGroup() {
		return this.groupList;
	}

	public int getTimePeriod() {
		return this.timePeriod;
	}

	public boolean isFlashing() {
		return this.flashing;
	}

	public void paintGroupImmediately() {
		for (JComponent aLabel : groupList) {
			aLabel.paintImmediately(aLabel.getBounds());
		}
	}

	public void setDutyCycle(double fraction) {
		if (fraction >= 0 && fraction <= 1)
			this.dutyCycle = fraction;
		else
			throw new IllegalArgumentException("While passing fraction,"
					+ " the value should be between 0 to 1");
	}

	public void setDutyCycle(int percentage) {
		if (percentage >= 0 && percentage <= 100)
			this.dutyCycle = (double) percentage / 100;
		else
			throw new IllegalArgumentException("While passing integer,"
					+ " the value should be between 0 to 100");
	}

	public void setFlashing(boolean flashing) {
		this.flashing = flashing;
	}

	public void setGroup(ArrayList<JComponent> labels) {
		this.groupList = labels;
	}

	public void setGroupColor(Color color) {
		for (Component aLabel : groupList)
			aLabel.setForeground(color);
	}

	public void setTimePeriod(int time) {
		this.timePeriod = time;
	}

	/**
	 * NON Getter setters
	 */

	public void setVisible(boolean isVisible) {
		for (JComponent aLabel : groupList) {
			aLabel.setVisible(isVisible);
		}
	}

	public void startFlashing() {
		this.flashing = true;
	}

	public void stopFlashing() {
		this.flashing = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}