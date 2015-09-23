package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ActionMap;
import javax.swing.JComponent;

import matlabcontrol.MatlabProxy;

public class SessionManager extends Thread implements ActionListener {

	enum P300GroupMergePolicy {
		CONCATENATE, RANDOMIZE, ROUNDROBIN;
	}

	String endScript = "";

	FlasherGroup flashersShuffle = new FlasherGroup();
	ArrayList<FlasherGroup> groups = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsFlash = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsShuffle = new ArrayList<FlasherGroup>();

	boolean infiniteLoop = true;
	long intervalDuration = 1500;
	ReentrantLock lock = new ReentrantLock(true);

	ArrayList<Class<?>> logReceiveFormat = new ArrayList<Class<?>>();
	ArrayList<Object> logSendFormat = new ArrayList<Object>();

	MatlabProxy matlabSession = null;
	ArrayList<Class<?>> messageReceiveFormat = new ArrayList<Class<?>>();

	// boolean logging = true;
	// BufferedWriter loggingStream = null;

	ArrayList<Object> messageSendFormat = new ArrayList<Object>();

	long minSSVEPtime = 750;
	P300GroupMergePolicy P300merging = P300GroupMergePolicy.RANDOMIZE;
	boolean run = false;

	long sessionStartTime = 0;
	Singleton[][] singletons = null;
	protected boolean SSVEPrunning = false;

	String startScript = "";

	String taskScript = "";;

	BCIUI ui = new BCIUI("", true);

	// ReentrantLock lock = new ReentrantLock();

	public SessionManager(boolean undecorate, String title, String[][] options,
			ActionMap[][] actionMap, JComponent[] components, Color[] colors,
			ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
			GroupFreqPolicy[] freqPolicy, float[] startingFrequencies,
			float[] stoppingFrequencies, SignalType[] signalType, int vGap,
			int hGap) {

		// TODO Auto-generated constructor stub
		this.ui.dispose();
		this.ui = new BCIUI(title, undecorate);
		buildUi(title, options, actionMap, components, colors, groupsList,
				freqPolicy, startingFrequencies, stoppingFrequencies,
				signalType, vGap, hGap);
		this.ui.runStop.addActionListener(this);
		this.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "RUN":
			synchronized (this) {
				this.notifyAll();
				synchronized (this.lock) {
					this.lock.notifyAll();
				}
			}
			run = true;
			break;

		case "STOP":
			run = false;
			break;

		default:
			break;
		}
	}

	public void buildUi(BCIUI ui, Singleton sample,
			ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
			ActionMap actionMap, char[][] keys) {
//		char opt = 1;
	}

	public void buildUi(String title, String[][] options,
			ActionMap[][] actionMap, JComponent[] components, Color[] colors,
			ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
			GroupFreqPolicy[] freqPolicy, float[] startingFrequencies,
			float[] stoppingFrequencies, SignalType[] signalType, int vGap,
			int hGap) {

		ui.setTitle(title);
		ui.choices.removeAll();

		Singleton singleton = new Singleton(new int[] { 1, 1 }, components,
				colors);
		singletons = (Singleton[][]) Factory.makeBoard(options, singleton);
		this.ui.choices.setLayout(new GridLayout(singletons.length,
				singletons[0].length, hGap, vGap));

		for (Singleton[] singletonRow : singletons)
			for (Singleton aSingleton : singletonRow)
				this.ui.choices.add(aSingleton);

		this.ui.runStop.addActionListener(this);

		this.groups = Factory.makeGroups(groupsList, singletons, freqPolicy,
				signalType);

		for (FlasherGroup flasherGroup : this.groups)
			if (flasherGroup.type == SignalType.P300)
				groupsShuffle.add(flasherGroup);

		for (FlasherGroup flasherGroup : this.groups)
			if (flasherGroup.type == SignalType.SSVEP) {
				groupsFlash.add(flasherGroup);
				// System.out.println("SSVEP Group found. Members: "
				// + flasherGroup.size());
				flasherGroup.calculateFrequencies();
			}

	}

	public ArrayList<FlasherGroup> getSSVEPGroups() {
		ArrayList<FlasherGroup> ssvepGroups = new ArrayList<FlasherGroup>();
		for (FlasherGroup flasherGroup : groups)
			ssvepGroups.add(flasherGroup);
		return ssvepGroups;
	}

	protected void p300Excite() {
		if (!this.flashersShuffle.isEmpty())
			this.flashersShuffle.get(0).setFlash(flashersShuffle);
		else
			System.out.println("this.flashersShuffle is empty");
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
				synchronized (this.lock) {
					try {
						lock.wait(this.minSSVEPtime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// FIXME: Attention Hungry Loops
				while (!flashersShuffle.isEmpty())
					;
				// System.out.println("Waiting for Flashers Shuffle to get Empty");
				// synchronized (flashersShuffle) {
				// try {
				// flashersShuffle.wait();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// }
				ssvepDeexcite();
				synchronized (this) {
					try {
						this.wait(intervalDuration);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void ssvepDeexcite() {
		// System.out.println("SSVEPs deexited");
		for (FlasherGroup aGroup : groupsFlash)
			aGroup.setFlash(false);
	}

	protected void ssvepExcite() {
		// System.out.println("SSVEPs exited");
		for (FlasherGroup aGroup : groupsFlash)
			aGroup.setFlash(true);
	}

	public void terminate() {
		this.infiniteLoop = false;
		this.notify();
	}
}
