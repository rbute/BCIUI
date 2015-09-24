package edu.nitrkl.graphics.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.json.JSONObject;

public class BCIUI extends JFrame implements ActionListener, Cloneable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = -5789990501425003741L;

	public JLabel result = new JLabel("", null, JLabel.CENTER);
	public JPanel choices = new JPanel(new GridLayout());
	public JMenuBar menuBar = new JMenuBar();
	public JMenu filesMenu = new JMenu("Files");
	public FileSelectMenu loadPresetMenu = null;
	RunStopBtn runStop = new RunStopBtn();

	// public BCIUI(String[] args) {
	// this("", true);
	//
	// }

	public BCIUI(JSONObject settings) {
		this(settings.getString("title"), settings.getBoolean("undecorate"));
	}

	public BCIUI(String title, boolean unDecorate) {
		JMenuItem menuItem = null;

		this.setTitle(title);
		this.setUndecorated(unDecorate);
		this.setIconImage((new ImageIcon(getClass().getResource("icon.jpg")))
				.getImage());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);
		this.setJMenuBar(this.menuBar);
		this.add(result, BorderLayout.PAGE_START);
		this.add(choices, BorderLayout.CENTER);

		this.filesMenu.setForeground(Color.GRAY);
		this.menuBar.setBackground(Color.black);
		this.menuBar.add(filesMenu);
		this.result.setBackground(new Color(0x00404040));
		this.result.setForeground(new Color(0x002fff5f));
		this.result.setOpaque(true);
		this.result.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
		this.result.setPreferredSize(new Dimension(0, 54));
		this.choices.setBackground(Color.black);

		loadPresetMenu = new FileSelectMenu("Load Preset", "settings",
				new String[] { ".json" }, "LOADPRESETS",
				new ActionListener[] { this });
		this.filesMenu.add(loadPresetMenu);
		menuItem = new JMenuItem("Exit");
		menuItem.setActionCommand("EXIT");
		menuItem.addActionListener(this);
		this.filesMenu.add(menuItem);
		this.menuBar.add(filesMenu);
		this.menuBar.add(runStop);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "EXIT":
			System.exit(0);
			break;
		case "LOADPRESETS":
			// System.out.println(((JMenuItem) e.getSource()).getText());
			System.out.println("Selected Settings: "
					+ ((JMenuItem) e.getSource()).getText());
			System.out.println("Settings Named: "
					+ ((JMenuItem) e.getSource()).getName());
			break;
		default:
			System.out.println(e.getActionCommand());
			break;
		}
	}

}