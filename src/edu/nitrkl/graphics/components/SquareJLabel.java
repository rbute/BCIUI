package edu.nitrkl.graphics.components;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class SquareJLabel extends JLabel implements CloneableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double relativeTextSize = 0.5;

	public SquareJLabel(String string, int center) {
		super(string, center);
	}

	public SquareJLabel() {
		super();
		super.setHorizontalAlignment(CENTER);
		super.setVerticalAlignment(CENTER);
	}

	public SquareJLabel(String str) {
		super(str);
		super.setHorizontalAlignment(CENTER);
		super.setVerticalAlignment(CENTER);
	}

	public void setBounds(int x, int y, int width) {
		super.setBounds(x, y, width, width);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width <= height ? width : height,
				width <= height ? width : height);
		int i = (int) (this.getBounds().getHeight() * this.relativeTextSize);

		super.setFont(new Font(super.getFont().getFontName(), super.getFont()
				.getStyle(), i));
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width < height ? width : height, width < height ? width
				: height);
	}

	@Override
	public void setFont(Font font) {
		super.setFont(new Font(font.getName(), font.getStyle(), this.getWidth()
				/ (this.getText().length() > 0 ? this.getText().length() : 1)));
	}

	@Override
	public JComponent getClone() {
		SquareJLabel temp = new SquareJLabel(this.getText());
		temp.setVisible(this.isVisible());
		temp.setForeground(this.getForeground());
		temp.setBackground(this.getBackground());
		return temp;
	}

}
