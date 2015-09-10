package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ActionMap;
import javax.swing.JComponent;

import matlabcontrol.MatlabProxy;

public class SessionManager extends Thread implements ActionListener {

	Singleton[][] Singletons = null;
	BCIUI ui = null;

	ArrayList<FlasherGroup> groups = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsShuffle = new ArrayList<FlasherGroup>();
	ArrayList<Flasher> flashersShuffle = new ArrayList<Flasher>();

	String startScript = "";
	String taskScript = "";
	String endScript = "";

	ArrayList<Object> messageSendFormat = new ArrayList<Object>();
	ArrayList<Class<?>> messageReceiveFormat = new ArrayList<Class<?>>();

	ArrayList<Object> logSendFormat = new ArrayList<Object>();
	ArrayList<Class<?>> logReceiveFormat = new ArrayList<Class<?>>();

	boolean logging = true;
	BufferedWriter loggingStream = null;

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

	public SessionManager(BCIUI ui, boolean undecorate, String Title,
			String[][] options, ActionMap[][] actionMap,
			JComponent[] components, Color[] colors,
			ArrayList<ArrayList<int[]>> groups, SignalType[] signalType) {
		// TODO Auto-generated constructor stub

	}

	public void petP300Shuflled() {
		for (FlasherGroup flasherGroup : groupsShuffle)
			Collections.shuffle(flasherGroup, new Random());

		switch (P300merging) {
		case CONCATENATE:
			ArrayList<Flasher> temp = new ArrayList<Flasher>();
			for (FlasherGroup flasherGroup : groupsShuffle)
				if (flasherGroup.type == SignalType.P300)
					temp.addAll(flasherGroup);
			flashersShuffle = temp;
			break;

		case RANDOMIZE:
			ArrayList<Flasher> temp2 = new ArrayList<Flasher>();
			for (FlasherGroup flasherGroup : groupsShuffle)
				if (flasherGroup.type == SignalType.P300)
					temp2.addAll(flasherGroup);
			Collections.shuffle(temp2);
			flashersShuffle = temp2;
			break;

		case ROUNDROBIN:
			ArrayList<Flasher> temp3 = new ArrayList<Flasher>();
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

	public void buildUi(String Title, String[][] options,
			ActionMap[][] actionMap, JComponent[] components, Color[] colors,
			ArrayList<ArrayList<int[]>> groups, SignalType[] signalType) {

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

	}

	@Override
	public void run() {
		while (infiniteLoop) {
			while (run) {

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
