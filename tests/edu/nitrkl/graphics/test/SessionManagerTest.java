package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;

import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.GroupFreqPolicy;
import edu.nitrkl.graphics.components.ResizableTextJLabel;
import edu.nitrkl.graphics.components.SessionManager;
import edu.nitrkl.graphics.components.SignalType;

public class SessionManagerTest {

	public static void main(String[] args) {

		ArrayList<int[]> indexes = null;
		ArrayList<ArrayList<int[]>> p300group = new ArrayList<ArrayList<int[]>>();
		ArrayList<ArrayList<int[]>> ssvepGroup = new ArrayList<ArrayList<int[]>>();

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 1 });
		indexes.add(new int[] { 1, 2 });
		indexes.add(new int[] { 1, 3 });
		indexes.add(new int[] { 1, 4 });
		indexes.add(new int[] { 1, 5 });
		indexes.add(new int[] { 1, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 2, 1 });
		indexes.add(new int[] { 2, 2 });
		indexes.add(new int[] { 2, 3 });
		indexes.add(new int[] { 2, 4 });
		indexes.add(new int[] { 2, 5 });
		indexes.add(new int[] { 2, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 3, 1 });
		indexes.add(new int[] { 3, 2 });
		indexes.add(new int[] { 3, 3 });
		indexes.add(new int[] { 3, 4 });
		indexes.add(new int[] { 3, 5 });
		indexes.add(new int[] { 3, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 4, 1 });
		indexes.add(new int[] { 4, 2 });
		indexes.add(new int[] { 4, 3 });
		indexes.add(new int[] { 4, 4 });
		indexes.add(new int[] { 4, 5 });
		indexes.add(new int[] { 4, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 5, 1 });
		indexes.add(new int[] { 5, 2 });
		indexes.add(new int[] { 5, 3 });
		indexes.add(new int[] { 5, 4 });
		indexes.add(new int[] { 5, 5 });
		indexes.add(new int[] { 5, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 6, 1 });
		indexes.add(new int[] { 6, 2 });
		indexes.add(new int[] { 6, 3 });
		indexes.add(new int[] { 6, 4 });
		indexes.add(new int[] { 6, 5 });
		indexes.add(new int[] { 6, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 6, 1 });
		indexes.add(new int[] { 6, 2 });
		indexes.add(new int[] { 6, 3 });
		indexes.add(new int[] { 6, 4 });
		indexes.add(new int[] { 6, 5 });
		indexes.add(new int[] { 6, 6 });
		p300group.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 1 });
		indexes.add(new int[] { 2, 1 });
		indexes.add(new int[] { 3, 1 });
		indexes.add(new int[] { 4, 1 });
		indexes.add(new int[] { 5, 1 });
		indexes.add(new int[] { 6, 1 });
		ssvepGroup.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 2 });
		indexes.add(new int[] { 2, 2 });
		indexes.add(new int[] { 3, 2 });
		indexes.add(new int[] { 4, 2 });
		indexes.add(new int[] { 5, 2 });
		indexes.add(new int[] { 6, 2 });
		ssvepGroup.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 3 });
		indexes.add(new int[] { 2, 3 });
		indexes.add(new int[] { 3, 3 });
		indexes.add(new int[] { 4, 3 });
		indexes.add(new int[] { 5, 3 });
		indexes.add(new int[] { 6, 3 });
		ssvepGroup.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 4 });
		indexes.add(new int[] { 2, 4 });
		indexes.add(new int[] { 3, 4 });
		indexes.add(new int[] { 4, 4 });
		indexes.add(new int[] { 5, 4 });
		indexes.add(new int[] { 6, 4 });
		ssvepGroup.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 5 });
		indexes.add(new int[] { 2, 5 });
		indexes.add(new int[] { 3, 5 });
		indexes.add(new int[] { 4, 5 });
		indexes.add(new int[] { 5, 5 });
		indexes.add(new int[] { 6, 5 });
		ssvepGroup.add(indexes);

		indexes = new ArrayList<int[]>();
		indexes.add(new int[] { 1, 6 });
		indexes.add(new int[] { 2, 6 });
		indexes.add(new int[] { 3, 6 });
		indexes.add(new int[] { 4, 6 });
		indexes.add(new int[] { 5, 6 });
		indexes.add(new int[] { 6, 6 });
		ssvepGroup.add(indexes);

		ArrayList<ArrayList<ArrayList<int[]>>> groups = new ArrayList<ArrayList<ArrayList<int[]>>>();

		groups.add(null);
		groups.add(p300group);
		groups.add(ssvepGroup);

		SessionManager manager = new SessionManager(true, "Hello World",
				new String[][] { { "A", "B", "C", "D", "E", "F" },
						{ "G", "H", "I", "J", "K", "L", },
						{ "M", "N", "O", "P", "Q", "R" },
						{ "S", "T", "U", "V", "W", "X" },
						{ "Y", "Z", "0", "1", "2", "3" },
						{ "4", "5", "6", "7", "8", "9" } }, null,
				new JComponent[] { new ResizableTextJLabel("X", 0.7f),
						Factory.makeCross(0.5, 0.5),
						Factory.makeCenteredRectangle(0.6, 1) }, new Color[] {
						Color.red, Color.yellow, Color.white }, groups,
				new GroupFreqPolicy[] { null, GroupFreqPolicy.EQUAL,
						GroupFreqPolicy.ARITHMETIC }, new SignalType[] { null,
						SignalType.P300, SignalType.SSVEP }, 10, 10);
		manager.run();

	}

}
