package edu.nitrkl.graphics.components;

import java.awt.Component;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JLabel;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;

import org.json.JSONArray;

public class Factory {

	protected static final Logger logger = Logger.getLogger("syslog.log");
	protected static MatlabProxy matlabProxy = null;

	static {
		try {
			logger.addHandler(new FileHandler("logs/log_"
					+ System.currentTimeMillis() + "_.log"));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}

		// try {
		// matlabProxy = (new MatlabProxyFactory()).getProxy();
		// matlabProxy.eval("cd 'script'");
		// } catch (MatlabConnectionException | MatlabInvocationException e) {
		// logger.severe(e.getMessage());
		// }
	}

	public static Logger getLogger() {
		return logger;
	}

	public static MatlabProxy getMatlabProxy() {
		return matlabProxy;
	}
	
	public static Attributes parseAttributes(String[] args){
		Attributes switches = new Attributes();
		for (String string : args) 
			if (string.matches("^-+.*=.*$")) {
				String[] str = string.split("(^-+)|=");
				switches.putValue(str[1], str[2]);
			}
		return switches;
	}

	public static MatlabProxy getNewMatlabProxy(String scriptDir)
			throws MatlabConnectionException, MatlabInvocationException,
			IOException {
		matlabProxy = (new MatlabProxyFactory()).getProxy();
		matlabProxy.eval("cd " + (new File(scriptDir)).getCanonicalPath());
		return matlabProxy;
	}

	public static Singleton[][] makeBoard(JSONArray arr, Singleton singleton) {
		String[][] options = new String[arr.length()][((JSONArray) arr.get(0))
				.length()];

		for (int i = 0; i < arr.length(); i++)
			for (int j = 0; j < ((JSONArray) arr.get(0)).length(); j++)
				options[i][j] = ((JSONArray) arr.get(i)).getString(j);

		return makeBoard(options, singleton);
	}

	public static Singleton[][] makeBoard(String[][] options,
			Singleton singleton) {
		Singleton[][] singletons = new Singleton[options.length][options[0].length];
		for (int i = 0; i < options.length; i++) {
			for (int j = 0; j < options[0].length; j++) {
				if (options[i][j] == null) {
					singletons[i][j] = Factory.makeEmptySingleton(singleton,
							new int[] { i, j });
				} else {
					singletons[i][j] = singleton.getClone();

					for (Component jComponents : singletons[i][j]
							.getComponents())
						if (jComponents instanceof JLabel)
							((JLabel) jComponents).setText(options[i][j]);

					((Singleton) singletons[i][j]).setIndex(new int[] { i, j });
				}
			}
		}
		return singletons;
	}

	public static Polygon2 makeCenteredRectangle(double width, double height) {
		Polygon2 polygon = new Polygon2();
		((Polygon2) polygon).addPoint(0.5 * (1 - width), 0.5 * (1 - height));
		((Polygon2) polygon).addPoint(0.5 * (1 - width), 0.5 * (1 + height));
		((Polygon2) polygon).addPoint(0.5 * (1 + width), 0.5 * (1 + height));
		((Polygon2) polygon).addPoint(0.5 * (1 + width), 0.5 * (1 - height));
		return polygon;
	}

	public static Polygon2 makeCross(double horizontalPatchWidth,
			double verticalPathcWidth) {
		return Factory.makeCross(horizontalPatchWidth, verticalPathcWidth,
				1000, 1000);
	}

	public static Polygon2 makeCross(double horizontalPatchWidth,
			double verticalPathcWidth, int width, int height) {
		Integer[] xMarkers = { 0,
				0 + (int) ((1 - horizontalPatchWidth) * 0.5 * (width)),
				0 + (int) ((1 + horizontalPatchWidth) * 0.5 * (width)),
				0 + (width) };
		Integer[] yMarkers = { 0,
				0 + (int) ((1 - verticalPathcWidth) * 0.5 * (height)),
				0 + (int) ((1 + verticalPathcWidth) * 0.5 * (height)),
				0 + (height) };

		Polygon p = new Polygon();
		p.addPoint(xMarkers[0], yMarkers[1]);
		p.addPoint(xMarkers[1], yMarkers[1]);
		p.addPoint(xMarkers[1], yMarkers[0]);
		p.addPoint(xMarkers[2], yMarkers[0]);
		p.addPoint(xMarkers[2], yMarkers[1]);
		p.addPoint(xMarkers[3], yMarkers[1]);
		p.addPoint(xMarkers[3], yMarkers[2]);
		p.addPoint(xMarkers[2], yMarkers[2]);
		p.addPoint(xMarkers[2], yMarkers[3]);
		p.addPoint(xMarkers[1], yMarkers[3]);
		p.addPoint(xMarkers[1], yMarkers[2]);
		p.addPoint(xMarkers[0], yMarkers[2]);

		p.getBounds().setBounds(0, 0, 1, 1);
		return new Polygon2(p);
	}

	public static Singleton makeEmptySingleton(Singleton singleton, int[] index) {
		Singleton aSingleton = new Singleton(index);
		for (int i = 0; i < (singleton.getComponents()).length; i++)
			aSingleton.add(new JComponent() {
				private static final long serialVersionUID = 1L;
			});
		return aSingleton;
	}

	/**
	 * 
	 * @param list
	 * @param singletons
	 * @param signalType
	 * @return
	 * 
	 *         For Complete Construction of the Hierarchy
	 */
//
//	public static ArrayList<FlasherGroup> makeGroups(
//			ArrayList<ArrayList<ArrayList<int[]>>> list,
//			Singleton[][] singletons, GroupFreqPolicy[] freqPolicy,
//			SignalType[] signalType) {
//		ArrayList<FlasherGroup> flasherGroupsCluster = new ArrayList<FlasherGroup>();
//		int i = 0;
//		for (ArrayList<ArrayList<int[]>> group : list) {
//			FlasherGroup flasherGroup = new FlasherGroup();
//			if (group != null) {
//				for (ArrayList<int[]> flasher : group) {
//					ArrayList<Singleton> flashersList = new ArrayList<Singleton>();
//					for (int[] index : flasher) {
//						flashersList
//								.add(singletons[index[0] - 1][index[1] - 1]);
//
//					}
//					Flasher aFlasher = new Flasher(flashersList, 100, 0.5, i);
//					flasherGroup.add(aFlasher);
//					flasherGroup.type = signalType[i];
//					flasherGroup.freqPolicy = freqPolicy[i];
//				}
//				flasherGroupsCluster.add(flasherGroup);
//			}
//			i++;
//		}
//		return flasherGroupsCluster;
//	}

	public static ArrayList<ArrayList<ArrayList<int[]>>> makeGroups(
			int[][][][] list) {
		ArrayList<ArrayList<ArrayList<int[]>>> applicationGroups = new ArrayList<ArrayList<ArrayList<int[]>>>();
		for (int[][][] index : list) {
			if (index == null) {
				applicationGroups.add(null);
			} else {
				ArrayList<ArrayList<int[]>> flasherGroups = new ArrayList<ArrayList<int[]>>();
				for (int[][] is : index) {
					ArrayList<int[]> flasher = new ArrayList<int[]>();
					for (int[] is2 : is)
						flasher.add(is2);
					flasherGroups.add(flasher);
				}
				applicationGroups.add(flasherGroups);
			}
		}
		return applicationGroups;
	}

	public static ArrayList<ArrayList<ArrayList<Singleton>>> makeGroups(
			int[][][][] list, Singleton[][] singletons) {
		ArrayList<ArrayList<ArrayList<Singleton>>> managersList = new ArrayList<ArrayList<ArrayList<Singleton>>>();

		for (int[][][] group : list) {
			ArrayList<ArrayList<Singleton>> groupsList = new ArrayList<ArrayList<Singleton>>();
			if (group != null) {
				for (int[][] flasher : group) {
					ArrayList<Singleton> flashersList = new ArrayList<Singleton>();
					for (int[] index : flasher)
						flashersList.add(singletons[index[0]][index[1]]);
					groupsList.add(flashersList);
				}
				managersList.add(groupsList);
			} else {
				managersList.add(null);
			}
		}

		return managersList;
	}

	/**
	 * 
	 * @param list
	 * @param singletons
	 * @param signalType
	 * @return
	 * 
	 *         For Complete Construction of the Hierarchy
	 */
//
//	public static ArrayList<FlasherGroup> makeGroups(int[][][][] list,
//			Singleton[][] singletons, GroupFreqPolicy[] freqPolicy,
//			SignalType[] signalType) {
//		ArrayList<FlasherGroup> flasherGroupsCluster = new ArrayList<FlasherGroup>();
//		int i = 0;
//		for (int[][][] group : list) {
//			FlasherGroup flasherGroup = new FlasherGroup();
//			if (group != null) {
//				for (int[][] flasher : group) {
//					ArrayList<Singleton> flashersList = new ArrayList<Singleton>();
//					for (int[] index : flasher)
//						flashersList.add(singletons[index[0]][index[1]]);
//					Flasher aFlasher = new Flasher(flashersList, 100, 0.5, i);
//					flasherGroup.add(aFlasher);
//					flasherGroup.type = signalType[i];
//					flasherGroup.freqPolicy = freqPolicy[i];
//				}
//				flasherGroupsCluster.add(flasherGroup);
//			}
//			i++;
//		}
//		return flasherGroupsCluster;
//	}

	public static ArrayList<ArrayList<ArrayList<int[]>>> makeGroups(
			String[][][][] list) {
		ArrayList<ArrayList<ArrayList<int[]>>> applicationGroups = new ArrayList<ArrayList<ArrayList<int[]>>>();
		for (String[][][] index : list) {
			// if (index == null) {
			// applicationGroups.add(null);
			// } else {
			ArrayList<ArrayList<int[]>> flasherGroups = new ArrayList<ArrayList<int[]>>();
			for (String[][] is : index) {
				ArrayList<int[]> flasher = new ArrayList<int[]>();
				for (String[] is2 : is) {
					int[] coords = new int[is2.length];
					for (int i = 0; i < coords.length; i++) {
						coords[i] = Integer.parseInt(is2[i]);
					}
					flasher.add(coords);
				}
				flasherGroups.add(flasher);
			}
			applicationGroups.add(flasherGroups);
			// }
		}
		return applicationGroups;
	}

	public static JLabel[] makeJLabels(String[] inputStrings) {
		JLabel[] temp = new JLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i] = new JLabel();
			temp[i].setText(inputStrings[i]);
			temp[i].setOpaque(true);
		}
		return temp;
	}

	public static JLabel[][] makeJLabels(String[][] inputStrings) {
		JLabel[][] temp = new JLabel[inputStrings[0].length][inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new JLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}

	public static ResizableTextJLabel[] makeResizingTextJLabels(
			String[] inputStrings) {
		ResizableTextJLabel[] temp = new ResizableTextJLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i] = new ResizableTextJLabel();
			temp[i].setText(inputStrings[i]);
			temp[i].setOpaque(true);
		}
		return temp;
	}

	public static ResizableTextJLabel[][] makeResizingTextJLabels(
			String[][] inputStrings) {
		ResizableTextJLabel[][] temp = new ResizableTextJLabel[inputStrings[0].length][inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new ResizableTextJLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}

	public static SquareJLabel[] makeSquareJLabels(String[] inputStrings) {
		SquareJLabel[] temp = new SquareJLabel[inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			temp[i].setText(inputStrings[i]);
		}
		return temp;
	}

	public static SquareJLabel[][] makeSquareJLabels(String[][] inputStrings) {
		SquareJLabel[][] temp = new SquareJLabel[inputStrings[0].length][inputStrings.length];
		for (int i = 0; i < inputStrings.length; i++) {
			for (int j = 0; j < inputStrings[0].length; j++) {
				temp[i][j] = new SquareJLabel();
				temp[i][j].setText(inputStrings[i][j]);
				temp[i][j].setOpaque(true);
			}
		}
		return temp;
	}



}
