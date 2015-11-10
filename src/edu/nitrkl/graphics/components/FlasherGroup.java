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
	long[] flashTimes = new long[0];
	// GroupFreqPolicy freqPolicy = GroupFreqPolicy.ARITHMETIC;
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
		// flashTimes = new long[flashFreqs.length];
		// for (int i = 0; i < flashFreqs.length; i++) {
		// flashTimes[i] = (int) (1000 / flashFreqs[i]);
		// }
	}

	/**
	 * 
	 * @param jsObj
	 */
	private FlasherGroup(JSONObject jsObj) {
		super();
		this.type = Enum.valueOf(SignalType.class,
				jsObj.getString("SignalType"));
		// this.freqPolicy = Enum.valueOf(GroupFreqPolicy.class,
		// jsObj.getString("GroupFreqPolicy"));
		this.spectLowerFreq = (float) jsObj.getDouble("spectLowerFreq");
		this.spectHigherFreq = (float) jsObj.getDouble("spectHigherFreq");
		this.dutyCycle = jsObj.getDouble("dutyCycle");
		this.flashingLayer = jsObj.getInt("flashingLayer");
	}

	/**
	 * 
	 * @param jsObj
	 * @param singletons
	 */
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

				int cycleTime = (int) (1000 / jsObj.getJSONArray("frequencies")
						.getDouble(i));

				this.add(new Flasher(singletonList, cycleTime, this.dutyCycle,
						this.flashingLayer));
			}

			// flashTimes = new long[this.size()];
			// for (int i = 0; i < flashTimes.length; i++) {
			// this.get(i).timePeriod = (int) this.flashTimes[i];
			// this.get(i).dutyCycle = this.dutyCycle;
			// this.get(i).flashingLayer = (byte) this.flashingLayer;
			// }
		}

		// Log a Message
		String msg = "Flash Times: ";
		for (long i : flashTimes)
			msg = msg + " " + i;
		Factory.getLogger().config(msg);
	}

	@Override
	public boolean add(Flasher e) {
		boolean result = false;
		result = super.add(e);
		e.dutyCycle = this.dutyCycle;
		// calculateFrequencies();
		return result;
	}

	// void calculateFrequencies() {
	//
	// if (this.size() != flashTimes.length)
	// flashTimes = new long[this.size()];
	// double[] frequencies = new double[super.size()];
	//
	// Factory.getLogger().info("falshtimes size: " + flashTimes.length);
	// Factory.getLogger().info(
	// "spectrum higher Frequency: " + spectHigherFreq);
	// Factory.getLogger().info("specrum lower frequency: " + spectLowerFreq);
	//
	// switch (freqPolicy) {
	// case ARITHMETIC:
	// for (int i = 0; i < frequencies.length; i++) {
	// frequencies[i] = spectLowerFreq
	// + (spectHigherFreq - spectLowerFreq)
	// * ((double) i / (double) flashTimes.length);
	// }
	// break;
	//
	// case GEOMETRIC:
	// for (int i = 0; i < frequencies.length; i++) {
	// frequencies[i] = (spectLowerFreq * Math.pow(
	// (spectHigherFreq / spectLowerFreq),
	// ((double) i / (double) flashTimes.length)));
	// }
	// break;
	//
	// case EQUAL:
	// for (int i = 0; i < frequencies.length; i++)
	// frequencies[i] = spectLowerFreq;
	//
	// break;
	//
	// default:
	// break;
	// }
	//
	// for (int i = 0; i < frequencies.length; i++) {
	// flashTimes[i] = (int) (1000.0d / frequencies[i]);
	// this.get(i).timePeriod = (int) flashTimes[i];
	// }
	//
	// String str = "";
	// for (long l : flashTimes) {
	// str = str + " " + l;
	// }
	// Factory.getLogger().info(
	// "Calculated Flash Times " + flashTimes.length
	// + " Elements. With policy of " + freqPolicy
	// + " distribution \n Calculated Frequencies: " + str);
	// }

	@Override
	public Flasher remove(int index) {
		Flasher result = super.remove(index);
		if (this.isEmpty())
			Factory.getLogger().info("Flasher Empty. Notifying all");
		this.notifyAll();
		return result;
	}

	@Override
	public synchronized boolean remove(Object arg0) {
		boolean result = super.remove(arg0);
		synchronized (this) {
			if (this.isEmpty()) {
				this.notifyAll();
				Factory.getLogger().info("Flasher Empty. Notifying all");
			}
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = super.remove(c);
		synchronized (this) {
			this.notifyAll();
			Factory.getLogger().info("Flasher Empty. Notifying all");
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

	public synchronized void setFlashTimes(long[] flashTimes) {
		this.flashTimes = flashTimes;
	}

}
