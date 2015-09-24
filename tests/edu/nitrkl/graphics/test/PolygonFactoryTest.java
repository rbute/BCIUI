package edu.nitrkl.graphics.test;

import javax.swing.JFrame;

import org.json.JSONObject;

import edu.nitrkl.graphics.components.Polygon2;

public class PolygonFactoryTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("PolygonFactoryTest");

		// JPanel panel = new JPanel();
		// panel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		// frame.add(panel);

		// frame.add(Factory.makeCross(0.7, 0.7));

		// frame.add(new Polygon2(
		// new JSONArray(
		// "[[0.25,0.25],[0.25,0],[0.75,0],[0.75,0.25],[1,0.25],[1,0.75],[0.75,0.75],[0.75,1],[0.25,1],[0.25,0.75],[0,0.75],[0,0.25]]")));

		frame.add(new Polygon2(
				new JSONObject(
						"{\"points\":[[0.25,0.25],[0.25,0],[0.75,0],[0.75,0.25],[1,0.25],[1,0.75],[0.75,0.75],[0.75,1],[0.25,1],[0.25,0.75],[0,0.75],[0,0.25]],\"color\":[100,200,100]}")));

	}
}
