package edu.nitrkl.graphics.test;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import edu.nitrkl.graphics.components.GroupControlPanel;

public class Group_Controls_Test {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("CroupControlTest");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(400,450);
		frame.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		frame.add(new GroupControlPanel("Hello Testing 1"));
		frame.add(new GroupControlPanel("Hello Testing 2"));
		frame.add(new GroupControlPanel("Hello Testing 3"));
		
	}

}
