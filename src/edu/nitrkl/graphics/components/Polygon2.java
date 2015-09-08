package edu.nitrkl.graphics.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class Polygon2 extends JComponent implements CloneableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2943245399183392769L;

	protected static double max(double[] i) {
		int index = 0;
		for (int j = 0; j < i.length; j++)
			if (i[j] > i[index])
				index = j;
		return i[index];
	}

	protected static int max(int[] i) {
		int index = 0;
		for (int j = 0; j < i.length; j++)
			if (i[j] > i[index])
				index = j;
		return i[index];
	}

	boolean hide = true;

	int npoints = 0;

	protected Polygon visible = new Polygon();

	double xpoints[] = null;

	double ypoints[] = null;

	public Polygon2(String[] str){
		
	}
	
	public Polygon2() {
		xpoints = new double[0];
		ypoints = new double[0];
		npoints = 0;
	}

	public Polygon2(double[] inputXPoints, double[] inputYPoints,
			int inputNPoints) {
		xpoints = inputXPoints;
		ypoints = inputYPoints;
		npoints = inputNPoints;
		visible.npoints = inputNPoints;
		visible.xpoints = new int[npoints];
		visible.ypoints = new int[npoints];
		calculatePoints();
	}

	public Polygon2(int[] inputXPoints, int[] inputYPoints, int inputNPoints) {
		this(new Polygon(inputXPoints, inputYPoints, inputNPoints));
	}

	public Polygon2(Polygon p) {

		visible = new Polygon(p.xpoints, p.ypoints, p.npoints);

		xpoints = new double[visible.npoints];
		ypoints = new double[visible.npoints];

		this.npoints = visible.npoints;
		for (int i = 0; i < visible.npoints; i++) {
			xpoints[i] = (double) (visible.xpoints[i])
					/ (double) (max(visible.xpoints));
			ypoints[i] = (double) (visible.ypoints[i])
					/ (double) (max(visible.ypoints));
		}
	}

	protected Polygon2(Polygon2 polygon) {

		visible = new Polygon(polygon.visible.xpoints, polygon.visible.ypoints,
				polygon.visible.npoints);

		this.xpoints = new double[polygon.xpoints.length];
		this.ypoints = new double[polygon.ypoints.length];
		this.npoints = polygon.npoints;

		for (int i = 0; i < polygon.npoints; i++) {
			this.xpoints[i] = polygon.xpoints[i];
			this.ypoints[i] = polygon.ypoints[i];
		}
		calculatePoints();

		this.setForeground(new Color(polygon.getForeground().getRed(), polygon
				.getForeground().getGreen(), polygon.getForeground().getBlue()));

		// FIXME: Fixed Value
		this.setVisible(true);
	}

	public void addPoint(double x, double y) {
		npoints++;
		double[] tempxpoints = new double[xpoints.length + 1];
		double[] tempypoints = new double[ypoints.length + 1];

		int i = 0;
		for (i = 0; i < xpoints.length; i++)
			tempxpoints[i] = xpoints[i];
		tempxpoints[i] = x;
		xpoints = tempxpoints;

		for (i = 0; i < ypoints.length; i++)
			tempypoints[i] = ypoints[i];
		tempypoints[i] = y;
		ypoints = tempypoints;
		visible.addPoint((int) (this.getBounds().x * x),
				(int) (this.getBounds().y * y));
		calculatePoints();
	}

	protected void calculatePoints() {
		// FIXME: not visible
		for (int i = 0; i < npoints; i++) {
			visible.xpoints[i] = (int) (this.getBounds().width * xpoints[i]);
			visible.ypoints[i] = (int) (this.getBounds().height * ypoints[i]);
		}
	}

	@Override
	public Polygon2 getClone() {
		return new Polygon2(this);
	}

	@Override
	public void paintImmediately(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.paintImmediately(arg0, arg1, arg2, arg3);
	}

	@Override
	public void paintImmediately(Rectangle r) {
		// TODO Auto-generated method stub
		super.paintImmediately(r);
	}

	public void reset() {
		npoints = 0;
		xpoints = new double[0];
		ypoints = new double[0];
		visible.reset();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		calculatePoints();
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		if (!(this.getForeground() == null))
			arg0.fillPolygon(visible);
	}

	@Override
	public String toString() {
		String str = "Integer Polygon Values:";
		for (int i = 0; i < visible.npoints; i++) {
			str = str + "\n[ " + visible.xpoints[i] + "\t, "
					+ visible.ypoints[i] + "\t]";
		}
		str = str + "\nDouble Precision Floating Point Values:";
		for (int i = 0; i < this.npoints; i++) {
			str = str + "\n[ " + this.xpoints[i] + "\t, " + this.ypoints[i]
					+ "\t]";
		}
		return str;
	}
}
