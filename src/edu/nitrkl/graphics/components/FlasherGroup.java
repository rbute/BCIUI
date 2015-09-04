package edu.nitrkl.graphics.components;

import java.util.ArrayList;

public class FlasherGroup extends ArrayList<Flasher> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7526886100298709892L;

	SignalType type;
	int[] FlashTime = new int[0];
	double dutyCycle = 0.5;
	
	public FlasherGroup() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean add(Flasher e) {
		e.dutyCycle=0.5;
		return super.add(e);
	}
	

}
