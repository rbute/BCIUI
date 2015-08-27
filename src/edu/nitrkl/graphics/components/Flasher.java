package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.concurrent.locks.ReentrantLock;

public class Flasher extends Thread implements EventListener {

	ArrayList<FlasherSingleton> elements = new ArrayList<FlasherSingleton>();
	double dutyCycle = 0.5;
	byte flashingLayer = 2;
	int timePeriod = 100;

	ArrayList<Flasher> flashSequence = new ArrayList<Flasher>();
	boolean flashOnce = false;
	boolean flash = false;
	byte flashCount = 0;
	long flashUntil = 0;

	boolean infiniteLoop = true;
	ReentrantLock lock = new ReentrantLock();

	public Flasher(ArrayList<FlasherSingleton> elements, int timePeriod,
			int dutyCycle, byte flashingLayer) {
		this((FlasherSingleton[]) elements.toArray(), timePeriod, dutyCycle,
				flashingLayer);
	}

	public Flasher(FlasherSingleton[] elements, int timePeriod,
			double dutyCycle, byte flashingLayer) {
		for (FlasherSingleton singleton : elements)
			this.elements.add(singleton);
		this.timePeriod = timePeriod;
		this.dutyCycle = dutyCycle;
		this.flashingLayer = flashingLayer;
		this.start();
	}

	public void terminate() {
		infiniteLoop = false;
		this.notify();
	}

	public void unsetFlash() {
		this.flashOnce = false;
		// this.notify();
	}

	public void setFlash() {
		this.flashOnce = true;
		// this.notify();
	}

	public void setFlash(boolean flash) {
		this.flash = flash;
		// this.notify();
	}

	public void setFlash(byte flashCount) {
		this.flashCount = flashCount;
		// this.notify();
	}

	public void setFlash(int flashTime) {
		this.flashUntil = System.currentTimeMillis() + flashTime;
		// this.notify();
	}

	public void setFlash(long flashUntilTime) {
		this.flashUntil = flashUntilTime;
		// this.notify();
	}

	public void setFlash(ArrayList<Flasher> flashSequence) {
		this.flashSequence = flashSequence;
		// this.flashSequence.remove(this);
		 this.setFlash();
		// this.notify();
	}

	protected void setGroupVisibility(boolean visible) {
		for (FlasherSingleton component : elements) {
			component.getComponent(this.flashingLayer).setVisible(visible);
		}
	}

	protected void flash() {
		setGroupVisibility(true);
		this.lock.lock();

		try {
			Thread.sleep((long) (timePeriod * dutyCycle));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.lock.unlock();
		setGroupVisibility(false);
		this.lock.lock();

		try {
			Thread.sleep((long) (timePeriod * (1 - dutyCycle)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.lock.unlock();
	}

	@Override
	public void run() {
		while (infiniteLoop) {
			while (infiniteLoop
					&& (flash || flashOnce || (flashCount > 0) || (System
							.currentTimeMillis() < flashUntil))
					|| (this.flashSequence.indexOf(this) >= 0)) {

				flash();

				if (System.currentTimeMillis() < flashUntil)
					;
				else if (flashOnce)
					flashOnce = false;
				else if (flashCount > 0)
					flashCount--;
				 else
				if (this.flashSequence.indexOf(this) >= 0) {
					this.flashSequence.remove(this);
					if (!this.flashSequence.isEmpty())
						this.flashSequence.get(0).setFlash(this.flashSequence);
				}
			}
		}
	}

}
