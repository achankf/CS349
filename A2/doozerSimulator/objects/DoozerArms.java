package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.Draw;

public final class DoozerArms extends BaseComponent{
	private double [] armAngles;

	DoozerArms(Point ptRef, int width, int height, double [] providedAngles){
		super(ptRef,width,height);
		armAngles = new double [providedAngles.length];
		System.arraycopy(providedAngles,0,armAngles,0, providedAngles.length);
	}

	public void move(Point pt){

	}

	public double getX(int i){
		return this.getRefX() + this.getWidth() *i - i * this.getWidth()/8;
	}

	public double getY(int i){
		return this.getRefY();
	}

	public double getPivotX(int i){
		return getRefX() + getWidth()*i - i*getWidth()/8;
	}

	public double getPivotY(int i){
		return this.getRefY() - getHeight() / 2;
	}

	public Point getPivot(int i){
		return new Point((int)getPivotX(i), (int)getPivotY(i));
	}

	public int getNumComp(){
		return armAngles.length;
	}

	public Point componentPos(int i, Point pt){
		double x = pt.x - getPivotX(i);
		double y = pt.y - getPivotY(i);
		return new Point((int)x,(int)y);
	}

	public void draw(Graphics2D g2d, Convert convert){
		AffineTransform before = g2d.getTransform();
		for (int i = 0; i < getNumComp(); i++){
			Point pt = convert.toCanvas(getPoint(i));
			Point pivot = convert.toCanvas(getPivot(i));
			Dimension dim = convert.scaleDim(this);
			Draw.point(g2d, pivot,10);
			Draw.rotate(g2d, armAngles[i], pivot); 
			Draw.drawRect(g2d, pt, dim);
		}
		g2d.setTransform(before);
	}
}
