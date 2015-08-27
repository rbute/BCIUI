package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JLabel;

public class Cross extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -247874848680268712L;

	double xStripWidth = 0.5;
	double yStripWidth = 0.5;
	Dimension dimension = null;

	Polygon r = null;
	Polygon p = new Polygon();
	Rectangle oldBounds = null;

	public Cross(double xStripWidth, double yStripWidth) {
		this.xStripWidth = xStripWidth;
		this.yStripWidth = yStripWidth;
	}

	public Cross(Polygon p) {
		r = p;
	}

	public void calculatePoints() {

		Integer[] xMarkers = {
				this.getBorder().getBorderInsets(this).left,
				this.getBorder().getBorderInsets(this).left
						+ (int) (0.5 * xStripWidth * (this.getBounds().width
								- this.getBorder().getBorderInsets(this).right - this
								.getBorder().getBorderInsets(this).left)),
				this.getBorder().getBorderInsets(this).left
						+ (int) ((1 - xStripWidth * 0.5) * (this.getBounds().width
								- this.getBorder().getBorderInsets(this).right - this
								.getBorder().getBorderInsets(this).left)),
				this.getBorder().getBorderInsets(this).left
						+ (this.getBounds().width
								- this.getBorder().getBorderInsets(this).right - this
								.getBorder().getBorderInsets(this).left) };

		Integer[] yMarkers = {
				this.getBorder().getBorderInsets(this).top,
				this.getBorder().getBorderInsets(this).top
						+ (int) (0.5 * yStripWidth * (this.getBounds().height
								- this.getBorder().getBorderInsets(this).bottom - this
								.getBorder().getBorderInsets(this).top)),
				this.getBorder().getBorderInsets(this).top
						+ (int) ((1 - yStripWidth * 0.5) * (this.getBounds().height
								- this.getBorder().getBorderInsets(this).bottom - this
								.getBorder().getBorderInsets(this).top)),
				this.getBorder().getBorderInsets(this).top
						+ (this.getBounds().height
								- this.getBorder().getBorderInsets(this).bottom - this
								.getBorder().getBorderInsets(this).top) };

		p = new Polygon();
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

	}

	protected int scale(int x0, int x1, int y0, int y1, int x) {
		return y0 + (x - x0) * (y0 - y1) / (x0 - x1);
	}

	protected int[] scale(int x0, int x1, int y0, int y1, int[] x) {
		for (int i = 0; i < x.length; i++)
			x[i] = y0 + (x[i] - x0) * (y0 - y1) / (x0 - x1);
		return x;
	}

	protected static int max(int[] i) {
		int index = 0;
		for (int j = 0; j < i.length; j++)
			if (i[j] > i[index])
				index = j;
		return i[index];
	}

	protected static int min(int[] i) {
		int index = 0;
		for (int j = 0; j < i.length; j++)
			if (i[j] < i[index])
				index = j;
		return i[index];
	}

	public void calculatePoints2() {
		try {
			scale(oldBounds.x, oldBounds.x + oldBounds.width,
					this.getBounds().x, this.getBounds().x
							+ this.getBounds().width, r.xpoints);
			
			scale(oldBounds.y, oldBounds.y + oldBounds.height,
					this.getBounds().y, this.getBounds().y
							+ this.getBounds().height, r.ypoints);

			oldBounds = this.getBounds();
		} catch (Exception e) {
			r = p;
			oldBounds = this.getBounds();
			System.out.println("Null Pointer");
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		calculatePoints2();
	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.blue);
		g.fillPolygon(r);
		super.paintComponent(g);
	}

}
