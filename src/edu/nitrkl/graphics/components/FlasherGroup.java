package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

public class FlasherGroup extends ArrayList<Flasher> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7526886100298709892L;

	SignalType type;
	int[] FlashTime = new int[0];
	protected double dutyCycle = 0.5;
	protected int timePeriod = 100;

	// public enum GroupFreqPolicy {
	// EQUAL, ARITHMETIC, GEOMETRIC
	// };

	GroupFreqPolicy freq = GroupFreqPolicy.ARITHMETIC;

	public void terminate() {
		for (Flasher aFlasher : this) {
			aFlasher.timePeriod = timePeriod;
			aFlasher.infiniteLoop = false;
			aFlasher.flashSequence.removeAll(aFlasher.flashSequence);
			// synchronized (aFlasher.lock) {
			// aFlasher.lock.notifyAll();
			// }
		}
		for (Flasher aFlasher : this)
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
	}

	public void setLock(ReentrantLock lock) {
		for (Flasher aFlasher : this)
			aFlasher.lock = lock;
	}

	public void unsetFlash() {
		for (Flasher aFlasher : this) {
			aFlasher.flashOnce = false;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void setFlash() {
		for (Flasher aFlasher : this) {
			aFlasher.flashOnce = true;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void setFlash(boolean flash) {
		for (Flasher aFlasher : this) {
			aFlasher.flash = flash;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void setFlash(byte flashCount) {
		for (Flasher aFlasher : this) {
			aFlasher.flashCount = flashCount;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void setFlash(int flashTime) {
		for (Flasher aFlasher : this) {
			aFlasher.flashUntil = System.currentTimeMillis() + flashTime;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void setFlash(long flashUntilTime) {
		for (Flasher aFlasher : this) {
			aFlasher.flashUntil = flashUntilTime;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void setFlash(ArrayList<Flasher> flashSequence) {
		for (Flasher aFlasher : this) {
			aFlasher.flashSequence = flashSequence;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

	public void waitUntilUnlocked() {

	}

	@Override
	public boolean add(Flasher e) {
		boolean result = false;
		result = super.add(e);
		e.dutyCycle = this.dutyCycle;
		e.timePeriod = this.timePeriod;
		return result;
	}

	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
		for (Flasher aFlasher : this) {
			aFlasher.timePeriod = timePeriod;
		}
	}

	public void setDutyCycle(double dutyCycle) {
		this.dutyCycle = dutyCycle;
		for (Flasher aFlasher : this) {
			aFlasher.dutyCycle = dutyCycle;
		}
	}

	@Override
	public boolean remove(Object arg0) {
		boolean result = super.remove(arg0);
		if (this.isEmpty())
			this.notifyAll();
		return result;
	}

	@Override
	public Flasher remove(int index) {
		Flasher result = super.remove(index);
		if (this.isEmpty())
			this.notifyAll();
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = super.remove(c);
		if (this.isEmpty())
			this.notifyAll();
		return result;
	}

}