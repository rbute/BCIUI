package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class ResizableTextJLabel extends JLabel implements CloneableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public float relativeTextSize = 1;

	public ResizableTextJLabel(String[] args) {
			
	}

	public ResizableTextJLabel() {
		super();
		super.setHorizontalAlignment(CENTER);
		super.setVerticalAlignment(CENTER);
	}

	public ResizableTextJLabel(String str, float relativeSize) {

		super(str);
		if (relativeSize > 1 || relativeSize < 0)
			throw new IllegalArgumentException(
					"Relative Size Must Be between 0 and 1");
		this.relativeTextSize = relativeSize;

		super.setHorizontalAlignment(CENTER);
		super.setVerticalAlignment(CENTER);
	}

	public ResizableTextJLabel(String str, float relativeSize, Color color) {
		this(str, relativeSize);
		this.setForeground(color);
	}

	public Component clone(String str) {
		ResizableTextJLabel cloned = null;
		try {
			cloned = (ResizableTextJLabel) this.clone();
			cloned.setText(str);
		} catch (CloneNotSupportedException e) {
			System.out.println("Failed to clone SquareJLabel");
			e.printStackTrace();
		}
		return cloned;
	}

	@Override
	public JComponent getClone() {
		ResizableTextJLabel temp = new ResizableTextJLabel(this.getText(),
				this.relativeTextSize);
		temp.setVisible(this.isVisible());
		temp.setForeground(new Color(this.getForeground().getRed(), this
				.getForeground().getGreen(), this.getForeground().getBlue()));
		return temp;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		super.setFont(new Font(super.getFont().getFontName(), super.getFont()
				.getStyle(), (int) (relativeTextSize * (height < width
				/ this.getText().length() ? height : width
				/ this.getText().length()))));

	}

	@Override
	public void setFont(Font font) {
		super.setFont(new Font(font.getName(), font.getStyle(),
				(int) (relativeTextSize * this.getWidth() / (this.getText()
						.length() > 0 ? this.getText().length() : 1))));
	}

	public void setRelativeTextSize(float d) {
		if (d < 0 || d > 1)
			throw new IllegalArgumentException(
					"The setRelativeTextSize(double d) accepts value from 0 to 1");
		this.relativeTextSize = d;
	}
}
