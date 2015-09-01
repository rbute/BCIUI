package edu.nitrkl.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import edu.nitrkl.graphics.components.RunStopBtn;

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
	public JMenu loadPreset = new JMenu("Load Preset");
	RunStopBtn runStop = new RunStopBtn();

	public BCIUI(boolean unDecorate) {
		JMenuItem menuItem = null;

		this.setTitle("P300-SSVEP GUI");
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
		this.choices.setBackground(Color.black);

		loadPreset.setActionCommand("LOADPRESETS");
		loadPreset.addActionListener(this);
		this.filesMenu.add(loadPreset);

		loadPreset.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				File[] settingFiles = (new File("settings"))
						.listFiles(new FileFilter() {
							@Override
							public boolean accept(File arg0) {
								return // true;
								arg0.getName().matches(
										"^.*[(.mat)(.xml)(.json)]$");
							}
						});
				for (File aFile : settingFiles) {
					JMenuItem item = new JMenuItem(aFile.getName());
					// FIXME: ActionCommands
					item.setActionCommand("LOADSETTINGS");
					loadPreset.add(item);
				}
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				loadPreset.removeAll();
			}

			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				loadPreset.removeAll();
			}
		});
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
		case "LOADSETTINGS":
			System.out.println("Load Settings Called");
			System.out.println(((JMenuItem) e.getSource()).getName());
			break;
		default:
			System.out.println(e.getActionCommand());
			break;
		}
	}

}