package edu.nitrkl.graphics.components;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class FileSelectMenu extends JMenu implements MenuListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2096509941825439223L;

	String matchExpressions = "";
	String directory = "";
	String itemsActionCommand = "";
	ActionListener[] listeners = null;

	public FileSelectMenu(String name, String sourceDirectory,
			String[] extensions, String actionCommand,
			ActionListener[] listeningObjects) {
		super(name);
		this.matchExpressions = "";
		this.matchExpressions += "^.*[";
		for (String str : extensions)
			this.matchExpressions += "(" + str + ")";
		this.matchExpressions += "]$";
		this.directory = sourceDirectory;
		this.itemsActionCommand = actionCommand;
		this.listeners = listeningObjects;
		this.addMenuListener(this);
	}

	@Override
	public void menuSelected(MenuEvent arg0) {
		this.removeAll();
		File[] settingFiles = (new File(directory)).listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				return arg0.getName().matches(matchExpressions);
			}
		});
		for (File aFile : settingFiles) {
			JMenuItem item = new JMenuItem(aFile.getName());
			item.setName(this.directory + "/" + aFile.getName());
			item.setActionCommand(this.itemsActionCommand);
			for (ActionListener aListener : listeners)
				item.addActionListener(aListener);
			this.add(item);
		}

	}

	@Override
	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub
		Factory.getLogger().info("New Listener Added");
		super.addActionListener(l);
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// Left Deactivated Intentionally
		// this.removeAll();
	}

	@Override
	public void menuCanceled(MenuEvent arg0) {
		this.removeAll();
	}
}
