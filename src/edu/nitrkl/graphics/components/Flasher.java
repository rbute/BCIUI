package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.concurrent.locks.ReentrantLock;

public class Flasher extends Thread implements EventListener {

	ArrayList<Singleton> elements = new ArrayList<Singleton>();
	double dutyCycle = 0.5;
	byte flashingLayer = 2;
	int timePeriod = 100;

	ArrayList<Flasher> flashSequence = new ArrayList<Flasher>();
	boolean flashOnce = false;
	boolean flash = false;
	byte flashCount = 0;
	long flashUntil = 0;

	boolean infiniteLoop = true;
	public ReentrantLock lock = new ReentrantLock();

	public Flasher(ArrayList<Singleton> elements, int timePeriod,
			int dutyCycle, byte flashingLayer) {
		this((Singleton[]) elements.toArray(), timePeriod, dutyCycle,
				flashingLayer);
	}

	public Flasher(Singleton[] elements, int timePeriod, double dutyCycle,
			byte flashingLayer) {
		for (Singleton singleton : elements)
			this.elements.add(singleton);
		this.timePeriod = timePeriod;
		this.dutyCycle = dutyCycle;
		this.flashingLayer = flashingLayer;
		this.start();
	}

	public void terminate() {
		infiniteLoop = false;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * Stops continuous flashing
	 */
	public void unsetFlash() {
		this.flashOnce = false;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * Flashes the array just once
	 */
	public void setFlash() {
		this.flashOnce = true;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * 
	 * @param flash
	 *            Sets the group continuous flashing mode
	 */
	public void setFlash(boolean flash) {
		this.flash = flash;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * 
	 * @param flashCount
	 * 
	 *            Flashes the whole group for a flashCount number of times
	 */
	public void setFlash(byte flashCount) {
		this.flashCount = flashCount;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * 
	 * @param flashTime
	 *            Flashes the entire group for a duration of flashTime
	 */
	public void setFlash(int flashTime) {
		this.flashUntil = System.currentTimeMillis() + flashTime;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * 
	 * @param flashUntilTime
	 * 
	 *            Flashes the whole array uptil time flashUntilTime this is
	 *            supplied to the milliseconds accuracy as in java.lang.date()
	 */
	public void setFlash(long flashUntilTime) {
		this.flashUntil = flashUntilTime;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	/**
	 * 
	 * @param flashSequence
	 * 
	 *            This Function flashes the array sequentially as supplied by
	 *            flashSequence
	 */
	public void setFlash(ArrayList<Flasher> flashSequence) {
		this.flashSequence = flashSequence;
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}

	protected void setGroupVisibility(boolean visible) {
		for (Singleton component : elements) {
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
				else if (this.flashSequence.indexOf(this) >= 0) {
					this.flashSequence.remove(this);
					if (!this.flashSequence.isEmpty())
						this.flashSequence.get(0).setFlash(this.flashSequence);
				}
			}
			synchronized (this.lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
