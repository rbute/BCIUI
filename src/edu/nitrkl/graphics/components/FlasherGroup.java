package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

public class FlasherGroup extends ArrayList<Flasher> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7526886100298709892L;

	protected double dutyCycle = 0.5;
	float endingFreq = 20;
	int[] flashTimes = new int[0];
	GroupFreqPolicy freq = GroupFreqPolicy.ARITHMETIC;
	float startingFreq = 5;

	public FlasherGroup(Collection<Flasher> c, float[] flashFreqs) {
		super(c);
		if (c.size() != flashFreqs.length)
			throw new IllegalArgumentException("Input Collections size "
					+ c.size() + " doesn't match with size of flashFreqs "
					+ flashFreqs.length);
		for (float f : flashFreqs)
			if (!(f > 3.32 || f < 40.1))
				throw new IllegalArgumentException(
						"No supplied frequency shall exceed 3.33 to 40 limit.");
		flashTimes = new int[flashFreqs.length];
		for (int i = 0; i < flashFreqs.length; i++) {
			flashTimes[i] = (int) (1000 / flashFreqs[i]);
		}
	}

	public FlasherGroup() {
		super();
	}

	public SignalType type;

	@Override
	public boolean add(Flasher e) {
		boolean result = false;
		result = super.add(e);
		e.dutyCycle = this.dutyCycle;
		// FIXME: Proper Frequency Settings
		// e.timePeriod = this.timePeriod;
		return result;
	}

	void calculateFrequencies() {
		switch (freq) {
		case ARITHMETIC:

			break;

		case GEOMETRIC:
			break;

		case EQUAL:

			break;

		default:
			break;
		}
	}

	@Override
	public Flasher remove(int index) {
		Flasher result = super.remove(index);
		if (this.isEmpty())
			this.notifyAll();
		return result;
	}

	@Override
	public boolean remove(Object arg0) {
		boolean result = super.remove(arg0);
		synchronized (this) {
			if (this.isEmpty())
				this.notifyAll();
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = super.remove(c);
		synchronized (this) {
			if (this.isEmpty())
				this.notifyAll();
		}
		return result;
	}

	public void setDutyCycle(double dutyCycle) {
		this.dutyCycle = dutyCycle;
		for (Flasher aFlasher : this) {
			aFlasher.dutyCycle = dutyCycle;
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

	public void setFlash(ArrayList<Flasher> flashSequence) {
		for (Flasher aFlasher : this) {
			aFlasher.flashSequence = flashSequence;
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

	public void setLock(ReentrantLock lock) {
		for (Flasher aFlasher : this)
			aFlasher.lock = lock;
	}

	public void calculateTimePeriod() {
		switch (freq) {
		case EQUAL:
			flashTimes = new int[this.size()];
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (startingFreq + (endingFreq - startingFreq)
						* (i / this.size()));

			break;

		case ARITHMETIC:
			flashTimes = new int[this.size()];
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (startingFreq + (endingFreq - startingFreq)
						* (i / this.size()));

			break;

		case GEOMETRIC:
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (startingFreq * java.lang.Math.pow(
						(endingFreq / startingFreq), (i / this.size())));

			break;

		default:
			break;
		}

	}

	public void unsetFlash() {
		for (Flasher aFlasher : this) {
			aFlasher.flashOnce = false;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

}
