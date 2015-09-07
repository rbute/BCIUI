package edu.nitrkl.graphics.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import matlabcontrol.MatlabProxy;

public class SessionManager extends Thread implements ActionListener {

	Singleton[][] Singletons = null;
	BCIUI ui = null;

	ArrayList<FlasherGroup> groups = new ArrayList<FlasherGroup>();
	ArrayList<FlasherGroup> groupsShuffle = new ArrayList<FlasherGroup>();
	ArrayList<Flasher> flashersShuffle = new ArrayList<Flasher>();
	ArrayList<FlasherGroup> currentGroupOrder = new ArrayList<FlasherGroup>();
	ArrayList<Flasher> currentFlasherOrder = new ArrayList<Flasher>();

	String startScript = "";
	String taskScript = "";
	String endScript = "";

	ArrayList<Object> messageSendFormat = new ArrayList<Object>();
	ArrayList<Class<?>> messageReceiveFormat = new ArrayList<Class<?>>();

	boolean logging = true;
	BufferedWriter loggingStream = new BufferedWriter(new OutputStreamWriter(
			new OutputStream() {

				@Override
				public void write(int arg0) throws IOException {
					// TODO Auto-generated method stub

				}
			}));

	MatlabProxy matlabSession = null;

	GroupManagement mode = GroupManagement.ORDERERD;

	public SessionManager() {
		// TODO Auto-generated constructor stub

	}

	public void addFLasherGroup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
