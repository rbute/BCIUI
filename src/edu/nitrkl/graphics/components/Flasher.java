package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.concurrent.locks.ReentrantLock;

public class Flasher extends Thread implements EventListener {

	double dutyCycle = 0.5;
	ArrayList<Singleton> elements = new ArrayList<Singleton>();
	boolean flash = false;

	byte flashCount = 0;
	byte flashingLayer = 2;
	boolean flashOnce = false;
	ArrayList<Flasher> flashSequence = new ArrayList<Flasher>();
	long flashUntil = 0;

	boolean infiniteLoop = true;
	public ReentrantLock lock = new ReentrantLock();
	public int timePeriod = 100;

	public Flasher(ArrayList<Singleton> elements, int timePeriod,
			double dutyCycle, int flashingLayer) {
		this.elements.addAll(elements);
		this.timePeriod = timePeriod;
		this.dutyCycle = dutyCycle;
		this.flashingLayer = (byte) flashingLayer;
		this.start();
		Factory.getLogger().config(
				"Flasher Created: " + this.elements + " time Period: "
						+ this.timePeriod + " duty Cycle: " + this.dutyCycle
						+ " layer: " + this.flashingLayer);
	}

	public Flasher(Singleton[] elements, int timePeriod, double dutyCycle,
			int flashingLayer) {
		for (Singleton singleton : elements)
			this.elements.add(singleton);
		this.timePeriod = timePeriod;
		this.dutyCycle = dutyCycle;
		this.flashingLayer = (byte) flashingLayer;
		this.start();
	}

	protected void flash() throws InterruptedException {
		if (this.timePeriod > 0 && this.timePeriod < 1000)
			setGroupVisibility(true);
		Thread.sleep((long) (timePeriod * dutyCycle));
		setGroupVisibility(false);
		Thread.sleep((long) (timePeriod * (1 - dutyCycle)));

	}

	@Override
	public void run() {
		while (infiniteLoop) {
			while (infiniteLoop
					&& (flash || flashOnce || (flashCount > 0) || (System
							.currentTimeMillis() < flashUntil))
					|| (this.flashSequence.indexOf(this) >= 0)) {

				if (!this.lock.isLocked())
					this.lock.lock();

				try {
					flash();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (System.currentTimeMillis() < flashUntil)
					;
				else if (flashOnce) {
					flashOnce = false;
				} else if (flashCount > 0) {
					flashCount--;
				} else if (this.flashSequence.indexOf(this) >= 0) {
					this.flashSequence.remove(this);
					if (!this.flashSequence.isEmpty())
						this.flashSequence.get(0).setFlash(this.flashSequence);
				}
			}
			if (this.lock.isLocked())
				this.lock.unlock();

			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Flashes the array just once
	 */
	public synchronized void setFlash() {
		this.flashOnce = true;

		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * 
	 * @param flashSequence
	 * 
	 *            This Function flashes the array sequentially as supplied by
	 *            flashSequence
	 */
	public synchronized void setFlash(ArrayList<Flasher> flashSequence) {
		this.flashSequence = flashSequence;
		synchronized (this) {
			this.notifyAll();
		}
	}

	/**
	 * 
	 * @param flash
	 *            Sets the group continuous flashing mode
	 */
	public synchronized void setFlash(boolean flash) {
		this.flash = flash;
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * 
	 * @param flashCount
	 * 
	 *            Flashes the whole group for a flashCount number of times
	 */
	public synchronized void setFlash(byte flashCount) {
		this.flashCount = flashCount;
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * 
	 * @param flashTime
	 *            Flashes the entire group for a duration of flashTime
	 */
	public synchronized void setFlash(int flashTime) {
		this.flashUntil = System.currentTimeMillis() + flashTime;
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * 
	 * @param flashUntilTime
	 * 
	 *            Flashes the whole array uptil time flashUntilTime this is
	 *            supplied to the milliseconds accuracy as in java.lang.date()
	 */
	public synchronized void setFlash(long flashUntilTime) {
		this.flashUntil = flashUntilTime;
		synchronized (this) {
			this.notify();
		}
	}

	protected void setGroupVisibility(boolean visible) {
		for (Singleton component : elements) {
			component.getComponent(this.flashingLayer).setVisible(visible);
			component.paintImmediately();
		}
	}

	public synchronized void terminate() {
		infiniteLoop = false;
		synchronized (this) {
			this.notify();
		}
	}

	/**
	 * Stops continuous flashing
	 */
	public synchronized void unsetFlash() {
		this.flashOnce = false;
		synchronized (this) {
			this.notify();
		}
	}
}
