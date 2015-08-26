package edu.nitrkl.graphics.test;

import javax.swing.JFrame;

import edu.nitrkl.graphics.components.ComponentFactory;

public class PolygonFactoryTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("PolygonFactoryTest");

//		JPanel panel = new JPanel();
//		panel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
//		frame.add(panel);

		frame.add(ComponentFactory.makeCross(0.7, 0.7));
		
	}
}

