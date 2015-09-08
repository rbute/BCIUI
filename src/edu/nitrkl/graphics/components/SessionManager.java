package edu.nitrkl.graphics.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import matlabcontrol.MatlabProxy;

public class SessionManager extends Thread implements ActionListener {

	Singleton[][] Singletons = null;
	BCIUI ui = null;

	ArrayList<FlasherGroup> groups = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsShuffle = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> currentGroupOrder = new ArrayList<FlasherGroup>();
	ArrayList<Flasher> flashersShuffle = new ArrayList<Flasher>();
	ArrayList<Flasher> currentFlasherOrder = new ArrayList<Flasher>();

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

	long intervalDuration = 0;

	enum GroupMergePolicy {
		ROUNDROBIN, RANDOMIZE, CONCATENATE;
	};

	public SessionManager() {
		// TODO Auto-generated constructor stub

	}

	public void addFlasherGroup() {
		// TODO Auto-generated method stub

	}

	public void terminate() {

	}

	@Override
	public void run() {
		while (infiniteLoop) {

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
