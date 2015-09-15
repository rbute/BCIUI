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
			ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
			GroupFreqPolicy[] freqPolicy, SignalType[] signalType, int vGap,
			int hGap) {

		// TODO Auto-generated constructor stub
		this.ui.dispose();
		this.ui = new BCIUI(title, undecorate);
		buildUi(title, options, actionMap, components, colors, groupsList,
				freqPolicy, signalType, vGap, hGap);
		this.ui.runStop.addActionListener(this);
		this.start();
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
			ArrayList<ArrayList<ArrayList<int[]>>> groupsList,
			GroupFreqPolicy[] freqPolicy, SignalType[] signalType, int vGap,
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

		// for (FlasherGroup group : this.groups) {
		// if (group == null) {
		// System.out.println("Null Group");
		// } else {
		// System.out.println("Group with Memebers: " + group.size()
		// + " and signal type " + group.type);
		// for (Flasher flasher : group) {
		//
		// if (group == null) {
		// System.out.println("Null Flasher");
		// } else {
		// System.out.println("Flasher with Memebers: "
		// + flasher.elements.size());
		// }
		// }
		// }
		// }

	}

	public void terminate() {
		this.infiniteLoop = false;
		this.notify();
	}

	protected void ssvepExcite() {
		// switch (SSVEPexcitation) {
		// case ROUNDROBIN:
		// if (!SSVEPrunning) {
		for (FlasherGroup aGroup : groups)
			if (aGroup.type == SignalType.SSVEP) {
				aGroup.setFlash(minSSVEPtime);
				synchronized (lock) {
					try {
						lock.wait(minSSVEPtime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		// SSVEPrunning = true;
		// } else {
		// SSVEPrunning = false;
		// }
		// break;
		//
		// case SIMULTENEOUS:
		// if (!SSVEPrunning) {
		// for (FlasherGroup aGroup : groups)
		// if (aGroup.type == SignalType.SSVEP) {
		// aGroup.setFlash();
		// }
		// SSVEPrunning = true;
		// } else {
		// for (FlasherGroup aGroup : groups)
		// if (aGroup.type == SignalType.SSVEP) {
		// aGroup.unsetFlash();
		// }
		// SSVEPrunning = false;
		// }
		// break;
		//
		// default:
		// break;
		// }
	}

	protected void p300Excite() {
		if (!this.flashersShuffle.isEmpty())
			this.flashersShuffle.get(0).setFlash(flashersShuffle);
		else
			System.out.println("this.flashersShuffle is empty");
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
				System.out.println("Running");
			}
			synchronized (this) {
				try {
					// System.out.println("Waiting");
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
		case "RUN":
			// System.out.println("Started Running.");
			synchronized (this) {
				this.notifyAll();
			}
			run = true;
			break;

		case "STOP":
			// System.out.println("Stopped Running.");
			run = false;
			break;

		default:
			break;
		}
	}
}
