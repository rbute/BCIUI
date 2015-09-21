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

		ArrayList<ArrayList<ArrayList<int[]>>> groups = new ArrayList<ArrayList<ArrayList<int[]>>>();

		groups.add(null);
		groups.addAll(Factory.makeGroups(new int[][][][] {
				{
						{ { 1, 1 }, { 1, 2 }, { 1, 3 }, { 1, 4 }, { 1, 5 },
								{ 1, 6 } },

						{ { 2, 1 }, { 2, 2 }, { 2, 3 }, { 2, 4 }, { 2, 5 },
								{ 2, 6 } },

						{ { 3, 1 }, { 3, 2 }, { 3, 3 }, { 3, 4 }, { 3, 5 },
								{ 3, 6 } },

						{ { 4, 1 }, { 4, 2 }, { 4, 3 }, { 4, 4 }, { 4, 5 },
								{ 4, 6 } },

						{ { 5, 1 }, { 5, 2 }, { 5, 3 }, { 5, 4 }, { 5, 5 },
								{ 5, 6 } },

						{ { 6, 1 }, { 6, 2 }, { 6, 3 }, { 6, 4 }, { 6, 5 },
								{ 6, 6 } } },

				{
						{ { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 },
								{ 6, 1 } },

						{ { 1, 2 }, { 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 },
								{ 6, 2 } },

						{ { 1, 3 }, { 2, 3 }, { 3, 3 }, { 4, 3 }, { 5, 3 },
								{ 6, 3 } },

						{ { 1, 4 }, { 2, 4 }, { 3, 4 }, { 4, 4 }, { 5, 4 },
								{ 6, 4 } },

						{ { 1, 5 }, { 2, 5 }, { 3, 5 }, { 4, 5 }, { 5, 5 },
								{ 6, 5 } },

						{ { 1, 6 }, { 2, 6 }, { 3, 6 }, { 4, 6 }, { 5, 6 },
								{ 6, 6 } } } }

		));

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
						GroupFreqPolicy.ARITHMETIC }, new float[] { 5, 3, 6 },
				new float[] { 5, 3, 20 }, new SignalType[] { null,
						SignalType.P300, SignalType.SSVEP }, 10, 10);
		// manager.run();

	}
}
