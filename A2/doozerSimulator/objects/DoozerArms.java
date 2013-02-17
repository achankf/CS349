package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.Draw;

public final class DoozerArms extends BaseComponent{
	private double [] armAngles;
	Dimension mdim;

	public DoozerArms(Point ptRef, int width, int height, double [] providedAngles){
		super(ptRef,width,height);
		armAngles = new double [providedAngles.length];
		System.arraycopy(providedAngles,0,armAngles,0, providedAngles.length);
		mdim = new Dimension(width/15,(int)(height*2));
	}

	@Override
	public SelectedPair containsAll(Point pt){
		Point temp = new Point(pt);
		int i = 0;
		for (; i < getNumComp(); i++){
			Point pivot = new Point(getPivot(i));
			rotatePoint(temp, pivot, armAngles[i]);
			if (contains(i,temp)) return new SelectedPair(this,i);
		}
		if (contains(temp, getMagnetPoint(), mdim)){
			return new SelectedPair(this,-1);
		}
		return null;
	}

	public void move(int i, Point pt){
			for (int j = 0; j < i; j++){
				rotatePoint(pt, getPivot(j),armAngles[j]);
			}
			armAngles[i] = calculateAngle(componentPos(i,pt));
	}

	@Override
	public double getX(int i){
		return this.getRefX() + this.getWidth() *i - i * this.getWidth()/8;
	}

	public double getMagnetX(){
		return getX(getNumComp()) + this.getWidth()/8;
	}

	public double getMagnetY(){
		return getY(getNumComp()) + this.getHeight() / 2;
	}

	public Point getMagnetPoint(){
		return new Point((int)getMagnetX(), (int)getMagnetY());
	}

	@Override
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
		Point pt;
		Point pivot;
		int i = 0;
		for (; i < getNumComp(); i++){
			pt = convert.toCanvas(getPoint(i));
			pivot = convert.toCanvas(getPivot(i));
			Dimension dim = convert.scaleDim(this);
			Draw.point(g2d, pivot,10);
			Draw.rotate(g2d, armAngles[i], pivot); 
			Draw.drawRect(g2d, pt, dim);
		}
		pt = convert.toCanvas(getMagnetPoint());
		Draw.drawRect(g2d, pt, convert.scaleDim(mdim));
		g2d.setTransform(before);
	}
}
