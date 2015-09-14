package edu.nitrkl.graphics.components;

import java.awt.Color;
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

	Singleton[][] singletons = null;
	BCIUI ui = new BCIUI("", true);

	ArrayList<FlasherGroup> groups = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsShuffle = new ArrayList<FlasherGroup>();
	FlasherGroup flashersShuffle = new FlasherGroup();

	String startScript = "";
	String taskScript = "";
	String endScript = "";

	ArrayList<Object> messageSendFormat = new ArrayList<Object>();
	ArrayList<Class<?>> messageReceiveFormat = new ArrayList<Class<?>>();

	ArrayList<Object> logSendFormat = new ArrayList<Object>();
	ArrayList<Class<?>> logReceiveFormat = new ArrayList<Class<?>>();

	// boolean logging = true;
	// BufferedWriter loggingStream = null;

	MatlabProxy matlabSession = null;

	boolean run = false;
	boolean infiniteLoop = true;
	ReentrantLock lock = new ReentrantLock(true);

	long intervalDuration = 750;
	long minSSVEPtime = 750;
	protected boolean SSVEPrunning = false;

	enum P300GroupMergePolicy {
		ROUNDROBIN, RANDOMIZE, CONCATENATE;
	};

	enum SSVEPGroupExitationPolicy {
		SIMULTENEOUS, ROUNDROBIN;
	}

	P300GroupMergePolicy P300merging = P300GroupMergePolicy.RANDOMIZE;
	SSVEPGroupExitationPolicy SSVEPexcitation = SSVEPGroupExitationPolicy.SIMULTENEOUS;

	public SessionManager(boolean undecorate, String title, String[][] options,
			ActionMap[][] actionMap, JComponent[] components, Color[] colors,
			ArrayList<ArrayList<ArrayList<int[]>>> groups,
			GroupFreqPolicy[] freqPolicy, SignalType[] signalType) {

		// TODO Auto-generated constructor stub
		this.ui = new BCIUI(title, undecorate);
		buildUi(title, options, actionMap, components, colors, groups,
				freqPolicy, signalType);
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

	public ArrayList<FlasherGroup> getSSVEPGroups() {
		ArrayList<FlasherGroup> ssvepGroups = new ArrayList<FlasherGroup>();
		for (FlasherGroup flasherGroup : groups)
			ssvepGroups.add(flasherGroup);
		return ssvepGroups;
	}

	public void buildUi(String title, String[][] options,
			ActionMap[][] actionMap, JComponent[] components, Color[] colors,
			ArrayList<ArrayList<ArrayList<int[]>>> groups,
			GroupFreqPolicy[] freqPolicy, SignalType[] signalType) {

		ui.setTitle(title);
		ui.choices.removeAll();

		Singleton singleton = new Singleton(new int[] { 1, 1 }, components,
				colors);
		singletons = (Singleton[][]) Factory.makeBoard(options, singleton);
		this.groups = Factory.makeGroups(groups, singletons, freqPolicy,
				signalType);

		// FIXME: Write Code
		// Singleton temp = new Singleton(new int{1,1},, colors)

	}

	public void terminate() {
		this.infiniteLoop = false;
		this.notify();
	}

	protected void ssvepExcite() {
		switch (SSVEPexcitation) {
		case ROUNDROBIN:
			if (!SSVEPrunning) {
				for (FlasherGroup aGroup : groups)
					if (aGroup.type == SignalType.SSVEP) {
						aGroup.setFlash(minSSVEPtime);
						// this.lock.lock();
						synchronized (lock) {
							try {
								lock.wait(minSSVEPtime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				SSVEPrunning = true;
			} else {
				SSVEPrunning = false;
			}
			break;

		case SIMULTENEOUS:
			if (!SSVEPrunning) {
				for (FlasherGroup aGroup : groups)
					if (aGroup.type == SignalType.SSVEP) {
						aGroup.setFlash();
					}
				SSVEPrunning = true;
			} else {
				for (FlasherGroup aGroup : groups)
					if (aGroup.type == SignalType.SSVEP) {
						aGroup.unsetFlash();
					}
				SSVEPrunning = false;
			}
			break;

		default:
			break;
		}
	}

	protected void p300Excite() {
		this.flashersShuffle.get(0).setFlash(flashersShuffle);
	}

	// protected void printMessage(ArrayList<Object> message) {
	// String str = "";
	// for (Object object : message)
	// str += object.toString() + " ";
	// }

	@Override
	public void run() {
		while (infiniteLoop) {
			while (run) {

				p300shuflle();
				ssvepExcite();
				p300Excite();
				// FIXME: Attention Hungry Loops
				synchronized (this.lock) {
					try {
						lock.wait(this.minSSVEPtime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// FIXME: Attention Hungry Loops
				// while (!flashersShuffle.isEmpty())
				// ;
				synchronized (flashersShuffle) {
					try {
						flashersShuffle.wait();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "START":
			run = true;
			this.notify();
			break;

		case "STOP":
			run = false;
			break;

		default:
			break;
		}
	}
}
