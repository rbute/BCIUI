package edu.nitrkl.graphics.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FunctionManager implements ActionListener {

	ArrayList<ArrayList<Flasher>> flasherClusters = new ArrayList<ArrayList<Flasher>>();

	public enum FlashMode {
		SIMULTANEOUS, INTERLEAVING, SYNCHRONOUS, PERIODIC, NONCOLLIDING
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
