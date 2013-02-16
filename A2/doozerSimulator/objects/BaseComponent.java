package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;

public abstract class BaseComponent extends Dimension{
	private Point ptRef;

	public abstract void move(Point pt);
	public abstract double getX(int i);
	public abstract double getY(int i);
	public abstract int getNumComp();
	public abstract void draw(Graphics2D g2d, Convert convert);

	protected double getRefX(){
		return ptRef.getX();
	}

	protected double getRefY(){
		return ptRef.getY();
	}

	public BaseComponent(Point ptRef, int width, int height){
		super(width,height);
		this.ptRef = ptRef;
	}

	public Point getPoint(int i){
		return new Point((int)getX(i),(int)getY(i));
	}

	public SelectedPair containsAll(Point pt){
		for (int i = 0; i < getNumComp(); i++){
			if (contains(i,pt)) return new SelectedPair(this,i);
		}
		return null;
	}

	protected Boolean contains(Point pt, Point tar, Dimension tarDim){
		return pt.getX() >= tar.getX() && pt.getX() <= tar.getX() + tarDim.getWidth()
			&& pt.getY() <= tar.getY() && pt.getY() >= tar.getY() - tarDim.getHeight();
	}

	public Boolean contains(int i, Point pt){
		System.out.println (pt+ " " + getPoint(i) + " " + this);
		return contains(pt, getPoint(i), this);
	}

	
}
