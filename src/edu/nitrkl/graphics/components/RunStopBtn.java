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
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "RUN":
			((JButton) arg0.getSource()).setActionCommand("STOP");
			((JButton) arg0.getSource()).setText("Stop");
			break;
		case "STOP":
			((JButton) arg0.getSource()).setActionCommand("RUN");
			((JButton) arg0.getSource()).setText("Run ");
			break;
		default:
			((JButton) arg0.getSource()).setActionCommand("RUN");
			this.actionPerformed(new ActionEvent((JButton) arg0.getSource(), 0,
					((JButton) arg0.getSource()).getActionCommand()));
			break;
		}
	}
}
