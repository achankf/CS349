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
		return contains(pt, getPoint(i), this);
	}

	public double calculateAngle(Point pt){
		double angle = Math.acos(pt.x / Math.sqrt(Math.pow(pt.x,2) + Math.pow(pt.y,2)));
		// negative due to the origin of canvas is location on the top-left
		return -(pt.y < 0 ? -angle : angle);
	}

	public void rotatePoint(Point pt, Point pivot, double angle){
		double cost = Math.cos(angle);
		double sint = Math.sin(angle);
		double tempX = pt.x - pivot.x;
		double tempY = pt.y - pivot.y;
		double newX = tempX * cost - tempY * sint + pivot.x;
		double newY = tempX * sint + tempY * cost + pivot.y;
		pt.setLocation(newX,newY);
	}
}
