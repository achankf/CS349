package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class DoozerNode{
	protected DoozerModel parent;
	protected Dimension dim;
	protected Point pivot;
	protected ArrayList<DoozerModel> children;

	public DoozerNode(int x, int y, int width, int height, DoozerModel parent){
		this.parent = parent;
		this.pivot = new Point(x,y);
		this.dim = new Dimension(width,height);
	}

	public abstract void draw(Graphics g);
}
