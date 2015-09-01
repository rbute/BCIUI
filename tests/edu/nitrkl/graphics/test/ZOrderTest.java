package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import edu.nitrkl.graphics.components.Singleton;
import edu.nitrkl.graphics.components.Factory;
import edu.nitrkl.graphics.components.Polygon2;
import edu.nitrkl.graphics.components.ResizableTextJLabel;
import edu.nitrkl.graphics.components.OcculdingLayout;

public class ZOrderTest {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(1, 2));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setBackground(null);

		Singleton panel = new Singleton(new int[]{1,1});
		panel.setLayout(new OcculdingLayout());
		panel.setBackground(Color.orange);

		ResizableTextJLabel label = new ResizableTextJLabel("9",0.9f);
		ResizableTextJLabel label1 = new ResizableTextJLabel("0",0.9f);

		label.setOpaque(false);
		label.setForeground(Color.BLACK);

		Polygon2 p = Factory.makeCross(0.5, 0.5);
		p.setForeground(Color.yellow);

		Polygon2 p2 = Factory.makeCenteredRectangle(1, 0.5);

//		p2.setForeground(Color.blue);
		p2.setForeground(null);
		p2.setForeground(Color.blue);
		p2.setVisible(true);
		
		panel.add(label);
		panel.add(p2);
		panel.add(p);

		panel.setBorder(BorderFactory.createLineBorder(Color.blue, 10));
		label1.setBorder(BorderFactory.createLineBorder(Color.orange, 10));

		frame.add(label1);
		frame.add(panel);

	}

}
