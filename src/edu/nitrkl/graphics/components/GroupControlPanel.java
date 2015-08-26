package edu.nitrkl.graphics.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GroupControlPanel extends JPanel implements ActionListener,
		ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1964646056335102671L;

	enum PropertyChangeInvokerSlider {
		CONTROL, DUTYCYCLE, BORDERWIDTH
	};

	FlasherTemplate targetGroup = null;
	JSlider timePeriod = new JSlider(0, 1000);
	JSlider dutyCycle = new JSlider(0, 100);
	JButton deleteButton = new JButton("Delete Group");

	public JSlider getTimePeriod() {
		return timePeriod;
	}

	public JSlider getDutyCycle() {
		return dutyCycle;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setTimePeriod(JSlider timePeriod) {
		this.timePeriod = timePeriod;
	}

	public void setDutyCycle(JSlider dutyCycle) {
		this.dutyCycle = dutyCycle;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public GroupControlPanel(FlasherTemplate inputGroup, String title) {
		this.targetGroup = inputGroup;
		this.setBorder(BorderFactory.createTitledBorder(title));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(timePeriod, BorderLayout.LINE_START);
		this.add(dutyCycle, BorderLayout.LINE_END);

		timePeriod.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), " Control"));
		timePeriod.setMajorTickSpacing(100);
		timePeriod.setMinorTickSpacing(50);
		timePeriod.setName("CNTL");
		timePeriod.setSnapToTicks(true);
		timePeriod.setPaintTicks(true);
		//FIXME
//		timePeriod.addChangeListener(targetGroup);
		timePeriod.setSnapToTicks(true);

		dutyCycle.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), " Duty Cycle"));
		dutyCycle.setMajorTickSpacing(10);
		dutyCycle.setMinorTickSpacing(5);
		dutyCycle.setName("DUTY");
		dutyCycle.setPaintTicks(true);
		//FIXME
//		dutyCycle.addChangeListener(targetGroup);

		deleteButton.setActionCommand("DBUTTON");
		deleteButton.setActionMap(new ActionMap());
	}

	public GroupControlPanel(String title) {
		this(null, title);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
	}
}