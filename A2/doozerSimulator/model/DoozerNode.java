package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;

public class DoozerNode{
	protected Dimension dim;
	protected Point pivot;
	protected double angle;
	protected ArrayList<DoozerNode> children;

	public DoozerNode(int x, int y, int width, int height){
		this.pivot = new Point(x,y);
		this.dim = new Dimension(width,height);
		this.children = new ArrayList<DoozerNode>();
	}

	public int getHeight(){
		return this.dim.height;
	}

	public int getWidth(){
		return this.dim.width;
	}

	public Point getPivot(){
		return pivot;
	}

	public void addChild(DoozerNode node){
		children.add(node);
	}

	public ArrayList<DoozerNode> getChildren(){
		return children;
	}

	public double getAngle(){
		return angle;
	}

	public void setAngle(double angle){
		this.angle = angle;
	}

	public int getX(){
		return pivot.x - getWidth()/10;
	}

	public int getY(){
		return pivot.y + getHeight()/2;
	}

	public Boolean contains(Point pt){
		return pt.x >= getX() && pt.x <= getX() + getWidth()
		&& pt.y <= getY() && pt.y >= getY() - getHeight();
	}
}
