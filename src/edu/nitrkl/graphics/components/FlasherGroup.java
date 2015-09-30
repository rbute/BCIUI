package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONArray;
import org.json.JSONObject;

public class FlasherGroup extends ArrayList<Flasher> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7526886100298709892L;

	protected double dutyCycle = 0.5;
	int flashingLayer = 0;
	int[] flashTimes = new int[0];
	GroupFreqPolicy freqPolicy = GroupFreqPolicy.ARITHMETIC;
	float spectHigherFreq = 20;
	float spectLowerFreq = 5;

	public SignalType type;

	public FlasherGroup() {
		super();
	}

	public FlasherGroup(Collection<Flasher> c, float[] flashFreqs) {
		super(c);
		if (c.size() != flashFreqs.length)
			throw new IllegalArgumentException("Input Collections size "
					+ c.size() + " doesn't match with size of flashFreqs "
					+ flashFreqs.length);
		for (float f : flashFreqs)
			if (!(f > 0.9 || f < 40.1))
				throw new IllegalArgumentException(
						"No supplied frequency shall exceed 1.0 to 40.0 limit.");
		flashTimes = new int[flashFreqs.length];
		for (int i = 0; i < flashFreqs.length; i++) {
			flashTimes[i] = (int) (1000 / flashFreqs[i]);
		}
	}

	private FlasherGroup(JSONObject jsObj) {
		super();
		this.type = Enum.valueOf(SignalType.class,
				jsObj.getString("SignalType"));
		this.freqPolicy = Enum.valueOf(GroupFreqPolicy.class,
				jsObj.getString("GroupFreqPolicy"));
		this.spectLowerFreq = (float) jsObj.getDouble("spectLowerFreq");
		this.spectHigherFreq = (float) jsObj.getDouble("spectHigherFreq");
		this.dutyCycle = jsObj.getDouble("dutyCycle");
		this.flashingLayer = jsObj.getInt("flashingLayer");
	}

	public FlasherGroup(JSONObject jsObj, Singleton[][] singletons) {
		this(jsObj);
		if (!jsObj.isNull("groups")) {

			JSONArray groups = jsObj.getJSONArray("groups");

			for (int i = 0; i < groups.length(); i++) {
				ArrayList<Singleton> singletonList = new ArrayList<Singleton>();
				for (int j = 0; j < groups.getJSONArray(i).length(); j++)
					singletonList.add(singletons[(groups.getJSONArray(i)
							.getJSONArray(j).getInt(0)) - 1][(groups
							.getJSONArray(i).getJSONArray(j).getInt(1)) - 1]);

				this.add(new Flasher(singletonList, 1000, this.dutyCycle,
						this.flashingLayer));
			}

			flashTimes = new int[this.size()];
			calculateFrequencies();
			for (int i = 0; i < flashTimes.length; i++) {
				this.get(i).timePeriod = this.flashTimes[i];
				this.get(i).dutyCycle = this.dutyCycle;
				this.get(i).flashingLayer = (byte) this.flashingLayer;
			}
		}
	}

	@Override
	public boolean add(Flasher e) {
		boolean result = false;
		result = super.add(e);
		e.dutyCycle = this.dutyCycle;
		// FIXME: Proper Frequency Settings
		// e.timePeriod = this.timePeriod;
		calculateFrequencies();
		return result;
	}

	void calculateFrequencies() {
		if (this.size() != flashTimes.length)
			flashTimes = new int[this.size()];

		switch (freqPolicy) {
		case ARITHMETIC:
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (1 / (spectLowerFreq + (spectHigherFreq - spectLowerFreq)
						* ((i + 1) / flashTimes.length)));
			break;

		case GEOMETRIC:
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (1 / (spectLowerFreq * java.lang.Math
						.pow((spectHigherFreq / spectLowerFreq),
								((i + 1) / flashTimes.length))));
			break;

		case EQUAL:
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (1 / (spectLowerFreq));

			break;

		default:
			break;
		}
	}

	public void calculateTimePeriod() {
		switch (freqPolicy) {
		case EQUAL:
			flashTimes = new int[this.size()];
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (spectLowerFreq + (spectHigherFreq - spectLowerFreq)
						* (i / this.size()));

			break;

		case ARITHMETIC:
			flashTimes = new int[this.size()];
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (spectLowerFreq + (spectHigherFreq - spectLowerFreq)
						* (i / this.size()));

			break;

		case GEOMETRIC:
			for (int i = 0; i < flashTimes.length; i++)
				flashTimes[i] = (int) (spectLowerFreq * java.lang.Math.pow(
						(spectHigherFreq / spectLowerFreq), (i / this.size())));

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
	public synchronized boolean remove(Object arg0) {
		boolean result = super.remove(arg0);
		synchronized (this) {
			if (this.isEmpty()) {
				this.notifyAll();
				// System.out.println("FlasherGroup Noifies all");
			}
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = super.remove(c);
		synchronized (this) {
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
			aFlasher.setFlash(flash);
		}
	}

	public void setFlash(byte flashCount) {
		for (Flasher aFlasher : this) {
			aFlasher.setFlash(flashCount);
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

	public void unsetFlash() {
		for (Flasher aFlasher : this) {
			aFlasher.flashOnce = false;
			synchronized (aFlasher.lock) {
				aFlasher.lock.notifyAll();
			}
		}
	}

}
