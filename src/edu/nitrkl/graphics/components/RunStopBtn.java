package edu.nitrkl.graphics.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RunStopBtn extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6282595627628375203L;

	public RunStopBtn() {
		super("Run");
		super.setActionCommand("RUN");
		super.addActionListener(this);
	}

	@Override
	public void addActionListener(ActionListener l) {
		try {
			super.removeActionListener(l);
		} catch (Exception e) {
		}
		super.addActionListener(l);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "RUN":
			this.setActionCommand("STOP");
			this.setText("Stop");
			break;
		case "STOP":
			this.setActionCommand("STRT");
			this.setText("Run ");
			break;
		default:
			break;
		}
	}
}
