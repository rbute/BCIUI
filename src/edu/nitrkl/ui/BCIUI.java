package edu.nitrkl.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class BCIUI extends JFrame implements ActionListener, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789990501425003741L;

	public JLabel result = new JLabel("", null, JLabel.CENTER);
	public JPanel choices = new JPanel(new GridLayout());
	public JMenuBar menuBar = new JMenuBar();
	JButton runStop = new JButton("Run ");

	public BCIUI() {
		this.setTitle("P300-SSVEP GUI");
		this.setIconImage((new ImageIcon(getClass().getResource("icon.jpg")))
				.getImage());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		((JFrame) this).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);
		this.setJMenuBar(this.menuBar);
		this.add(result, BorderLayout.PAGE_START);
		this.add(choices, BorderLayout.CENTER);

		this.result.setBackground(new Color(0x00404040));
		this.result.setForeground(new Color(0x002fff5f));
		this.result.setOpaque(true);
		this.result.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
		this.choices.setBackground(Color.black);

		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));
		menuBar.add(runStop);
		runStop.addActionListener(new ActionListener() {
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
					this.actionPerformed(new ActionEvent((JButton) arg0
							.getSource(), 0, ((JButton) arg0.getSource())
							.getActionCommand()));
					break;
				}

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		}
	}

}