package edu.nitrkl.graphics.components;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import javax.swing.JMenuItem;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SessionManager extends Thread implements ActionListener {

	enum P300GroupMergePolicy {
		CONCATENATE, RANDOMIZE, ROUNDROBIN;
	}

	long detectionRecess = 1500;
	FlasherGroup flashersShuffle = new FlasherGroup();
	ArrayList<FlasherGroup> groups = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsFlash = new ArrayList<FlasherGroup>();

	ArrayList<FlasherGroup> groupsShuffle = new ArrayList<FlasherGroup>();
	boolean infiniteLoop = true;
	ReentrantLock lock = new ReentrantLock(true);

	String matlabScript = null;

	MatlabProxy matlabSession = null;
	long minSSVEPtime = 750;
	P300GroupMergePolicy P300merging = P300GroupMergePolicy.RANDOMIZE;

	boolean run = false;
	long sessionStartTime = 0;
	Singleton[][] singletons = null;

	protected boolean SSVEPrunning = false;

	BCIUI ui = null;

	// public SessionManager(boolean undecorate, String title, String[][]
	// options,
	// ActionMap[][] actionMap, JComponent[] components, Color[] colors,
	// ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
	// GroupFreqPolicy[] freqPolicy, float[] startingFrequencies,
	// float[] stoppingFrequencies, SignalType[] signalType, int vGap,
	// int hGap) {
	//
	// this.ui = new BCIUI(title, undecorate);
	// this.ui.filesMenu.addActionListener(this);
	// buildUi(title, options, actionMap, components, colors, groupsList,
	// freqPolicy, startingFrequencies, stoppingFrequencies,
	// signalType, vGap, hGap);
	// this.ui.runStop.addActionListener(this);
	// this.start();
	// System.gc();
	// }

	public SessionManager(JSONObject jsObj) throws JSONException,
			ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {

		this.ui = new BCIUI(
				jsObj.getJSONObject("uioptions").getString("title"), jsObj
						.getJSONObject("uioptions").getBoolean("undecorate"));
		this.buildUi(jsObj);
		this.detectionRecess = jsObj.getJSONObject("uioptions").getInt(
				"detectionrecess");
		this.ui.loadPresetMenu.addActionListener(this);
		this.ui.runStop.addActionListener(this);
		this.start();
		System.gc();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "RUN":
			synchronized (this.lock) {
				this.lock.notifyAll();
			}
			synchronized (this) {
				this.notifyAll();
			}
			run = true;
			break;

		case "STOP":
			run = false;
			break;
		case "LOADPRESETS":

			Factory.getLogger().info(
					"Loading settings: "
							+ ((JMenuItem) e.getSource()).getText());
			try {
				buildUi(new JSONObject(new JSONTokener(new FileReader(
						((JMenuItem) e.getSource()).getName()))));
			} catch (JSONException | ClassNotFoundException
					| NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| IOException e1) {
				e1.printStackTrace();
				Factory.getLogger().severe(e1.getMessage());
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * Accepts JSonObject as settings to the ui
	 * 
	 * 
	 * @param jsObj
	 * @throws JSONException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */

	public void buildUi(JSONObject jsObj) throws JSONException,
			ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {

		Singleton singleton = new Singleton(
				(JSONObject) jsObj.getJSONObject("singletonmodel"));

		Singleton[][] singletons2 = Factory.makeBoard(
				(JSONArray) jsObj.get("keys"), singleton);

		ArrayList<FlasherGroup> groups2 = new ArrayList<FlasherGroup>();

		String[] names = JSONObject.getNames(jsObj.getJSONObject("groupmodel"));

		for (String aString : names) {

			groups2.add(new FlasherGroup(jsObj.getJSONObject("groupmodel")
					.getJSONObject(aString), singletons2));
		}

		this.detectionRecess = jsObj.getJSONObject("uioptions").getInt(
				"detectionrecess");

		this.singletons = singletons2;
		this.groups = groups2;
		this.ui.choices.setLayout(new GridLayout(singletons.length,
				singletons[0].length, jsObj.getJSONObject("uioptions").getInt(
						"horizontalgap"), jsObj.getJSONObject("uioptions")
						.getInt("verticalgap")));

		this.ui.choices.removeAll();
		for (Singleton[] singletonRow : singletons)
			for (Singleton aSingleton : singletonRow) {
				this.ui.choices.add(aSingleton);
				try {
					aSingleton.getComponent(0).setVisible(true);
				} catch (Exception e) {
					Factory.logger.info("Component(0) is nonexistent.");
				}
			}

		for (FlasherGroup flasherGroup : this.groups)
			if (flasherGroup.type == SignalType.P300)
				groupsShuffle.add(flasherGroup);

		// Group making
		// for (FlasherGroup flasherGroup : this.groups)
		for (int j = 0; j < this.groups.size(); j++) {

			FlasherGroup flasherGroup = this.groups.get(j);

			if (flasherGroup.type == SignalType.SSVEP
					|| flasherGroup.type == SignalType.P300) {
				groupsFlash.add(flasherGroup);

				if ((jsObj.getJSONObject("groupmodel").getJSONObject(names[j])
						.has("frequencies"))) {
					JSONArray freqs = jsObj.getJSONObject("groupmodel")
							.getJSONObject(names[j])
							.getJSONArray("frequencies");

					if (freqs.length() != jsObj.getJSONObject("groupmodel")
							.getJSONObject(names[j]).getJSONArray("groups")
							.length())
						System.out.println("Length Mismatch");

					long[] flashtimes = new long[freqs.length()];

					for (int i = 0; i < flashtimes.length; i++) {
						flashtimes[i] = (long) (1 / freqs.getDouble(i));
						flasherGroup.setFlashTimes(flashtimes);
					}

					flasherGroup.setFlashTimes(flashtimes);

				} else {

					System.out.println("Frequency definition not proper");
					// flasherGroup.calculateFrequencies();
				}
			}
		}

		// Setting up a matlab Session
		if (jsObj.getJSONObject("uioptions").has("matlabscript")) {

			this.matlabScript = jsObj.getJSONObject("uioptions").getString(
					"matlabscript");

			try {
				if (Factory.getMatlabProxy() == null)
					Factory.getNewMatlabProxy("script");

				Factory.getMatlabProxy().feval(this.matlabScript, "SETUP",
						System.currentTimeMillis(), jsObj.toString());

			} catch (MatlabInvocationException | MatlabConnectionException e) {
				Factory.getLogger().info(
						"Error executing matlab file. Either"
								+ " wrong syntax or inaccessible file");
				e.printStackTrace();
			}
		}

		if (jsObj.getJSONObject("uioptions").has("initclean")
				&& jsObj.getJSONObject("uioptions").getBoolean("initclean"))
			for (Singleton[] sArray : this.singletons)
				for (Singleton aSingleton : sArray)
					aSingleton.hideSublayers();

	}

	// public void buildUi(String title, String[][] options,
	// ActionMap[][] actionMap, JComponent[] components, Color[] colors,
	// ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
	// GroupFreqPolicy[] freqPolicy, float[] startingFrequencies,
	// float[] stoppingFrequencies, SignalType[] signalType, int vGap,
	// int hGap) {
	//
	// ui.setTitle(title);
	// ui.choices.removeAll();
	//
	// Singleton singleton = new Singleton(new int[] { 1, 1 }, components,
	// colors);
	// singletons = (Singleton[][]) Factory.makeBoard(options, singleton);
	// this.ui.choices.setLayout(new GridLayout(singletons.length,
	// singletons[0].length, hGap, vGap));
	//
	// this.ui.choices.removeAll();
	// for (Singleton[] singletonRow : singletons)
	// for (Singleton aSingleton : singletonRow)
	// this.ui.choices.add(aSingleton);
	//
	// this.ui.runStop.addActionListener(this);
	//
	// this.groups = Factory.makeGroups(groupsList, singletons, freqPolicy,
	// signalType);
	//
	// for (FlasherGroup flasherGroup : this.groups)
	// if (flasherGroup.type == SignalType.P300)
	// groupsShuffle.add(flasherGroup);
	//
	// for (FlasherGroup flasherGroup : this.groups)
	// if (flasherGroup.type == SignalType.SSVEP) {
	// groupsFlash.add(flasherGroup);
	// flasherGroup.calculateFrequencies();
	// }
	//
	// }

	// public void buildUi(String title, String[][] options,
	// ActionMap[][] actionMap, JComponent[] components, Color[] colors,
	// int[][][][] groupsList, GroupFreqPolicy[] freqPolicy,
	// float[] startingFrequencies, float[] stoppingFrequencies,
	// SignalType[] signalType, int vGap, int hGap) {
	//
	// ui.setTitle(title);
	// ui.choices.removeAll();
	//
	// Singleton singleton = new Singleton(new int[] { 1, 1 }, components,
	// colors);
	// singletons = (Singleton[][]) Factory.makeBoard(options, singleton);
	// this.ui.choices.setLayout(new GridLayout(singletons.length,
	// singletons[0].length, hGap, vGap));
	//
	// this.ui.choices.removeAll();
	// for (Singleton[] singletonRow : singletons)
	// for (Singleton aSingleton : singletonRow)
	// this.ui.choices.add(aSingleton);
	//
	// this.ui.runStop.addActionListener(this);
	//
	// this.groups = Factory.makeGroups(groupsList, singletons, freqPolicy,
	// signalType);
	//
	// for (FlasherGroup flasherGroup : this.groups)
	// if (flasherGroup.type == SignalType.P300)
	// groupsShuffle.add(flasherGroup);
	//
	// for (FlasherGroup flasherGroup : this.groups)
	// if (flasherGroup.type == SignalType.SSVEP) {
	// groupsFlash.add(flasherGroup);
	// flasherGroup.calculateFrequencies();
	// }
	//
	// }

	public ArrayList<FlasherGroup> getSSVEPGroups() {
		ArrayList<FlasherGroup> ssvepGroups = new ArrayList<FlasherGroup>();
		for (FlasherGroup flasherGroup : groups)
			ssvepGroups.add(flasherGroup);
		return ssvepGroups;
	}

	protected void p300Excite() {
		if (!this.flashersShuffle.isEmpty())
			this.flashersShuffle.get(0).setFlash(flashersShuffle);
	}

	public void p300shuflle() {
		for (FlasherGroup flasherGroup : groupsShuffle)
			Collections.shuffle(flasherGroup, new Random());

		switch (P300merging) {
		case CONCATENATE:
			FlasherGroup temp = new FlasherGroup();
			for (FlasherGroup flasherGroup : groupsShuffle)
				if (flasherGroup.type == SignalType.P300)
					temp.addAll(flasherGroup);
			flashersShuffle = temp;
			break;

		case RANDOMIZE:
			FlasherGroup temp2 = new FlasherGroup();
			for (FlasherGroup flasherGroup : groupsShuffle)
				if (flasherGroup.type == SignalType.P300)
					temp2.addAll(flasherGroup);
			Collections.shuffle(temp2);
			flashersShuffle = temp2;
			break;

		case ROUNDROBIN:
			FlasherGroup temp3 = new FlasherGroup();
			int iteration = 0;
			for (FlasherGroup flasherGroup : groupsShuffle)
				if (flasherGroup.type == SignalType.P300)
					if (flasherGroup.size() > iteration)
						iteration = flasherGroup.size();
			for (int i = 0; i < iteration; i++)
				for (FlasherGroup flasherGroup : groupsShuffle)
					if (flasherGroup.type == SignalType.P300)
						if (flasherGroup.size() > i)
							temp3.add(flasherGroup.get(i));
			flashersShuffle = temp3;
			break;

		default:
			break;
		}
	}

	@Override
	public void run() {
		while (infiniteLoop) {
			while (run) {

				sessionStartTime = System.currentTimeMillis();

				ssvepExcite();
				p300shuflle();
				p300Excite();

				// TODO: Improve with returning eval
				// STart Sampling
				try {
					if (Factory.getMatlabProxy() != null)
						Factory.getMatlabProxy().feval(matlabScript, "START",
								"" + System.currentTimeMillis(), "");

				} catch (MatlabInvocationException e1) {
					Factory.getLogger().info(
							"Cannot eval while starting a flasher sequence");
				}
				synchronized (this.lock) {
					try {
						lock.wait(this.minSSVEPtime);
					} catch (InterruptedException e) {
						Factory.getLogger().log(Level.WARNING, e.toString());
					}
				}
				// FIXME: Attention Hungry Loops
				while (!flashersShuffle.isEmpty())
					Factory.getLogger().info(
							"Waiting for Flasher Shuffle to be empty");
				ssvepDeexcite();

				// Stop Sampling
				try {
					if (Factory.getMatlabProxy() != null)
						Factory.getMatlabProxy().feval(matlabScript, "STOP",
								"" + System.currentTimeMillis(), "");

				} catch (MatlabInvocationException e1) {
					Factory.getLogger().info(
							"Cannot eval while starting a flasher sequence");
				}

				synchronized (this) {
					try {
						this.wait(detectionRecess);
					} catch (InterruptedException e) {
						Factory.getLogger().log(Level.WARNING, e.toString());
					}
				}
			}
			synchronized (this.lock) {
				try {
					this.lock.wait();
				} catch (InterruptedException e) {
					Factory.getLogger().log(Level.WARNING, e.toString());
				}
			}
		}
	}

	protected void ssvepDeexcite() {
		for (FlasherGroup aGroup : groupsFlash)
			aGroup.setFlash(false);
	}

	protected void ssvepExcite() {
		for (FlasherGroup aGroup : groupsFlash)
			aGroup.setFlash(true);
	}

	public void terminate() {
		this.infiniteLoop = false;
		this.notify();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "Session Manager";
	}
}
