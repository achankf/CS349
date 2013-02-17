package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.Draw;

public final class DoozerArms extends BaseComponent{
	private double [] armAngles;
	Dimension mdim;
	Point magnetTip, magnetTipAccept;

	public DoozerArms(Point ptRef, int width, int height, double [] providedAngles){
		super(ptRef,width,height);
		armAngles = providedAngles;
		mdim = new Dimension(width/15,(int)(height*2));
		magnetTip = new Point(0,0);
		magnetTipAccept = new Point(0,0);
	}

	@Override
	public Selected containsAll(Point pt){
		Point temp = new Point(pt);
		int i = 0;
		for (; i < getNumComp(); i++){
			Point pivot = new Point(getPivot(i));
			rotatePoint(temp, pivot, armAngles[i]);
			if (contains(i,temp)) return new Selected(this,i,temp,pivot,armAngles[i]);
		}
		if (contains(temp, getMagnetPoint(), mdim)){
			return new Selected(this,-1,null,null,0);
		}
		return null;
	}

	public void rotateNPoint(int i, Point pt){
		for (int j = 0; j < armAngles.length; j++){
			rotatePoint(pt,getPivot(j),armAngles[j]);
		}
	}

	public void move(int i, Point pt, AffineTransform at){
		AffineTransform trans = new AffineTransform();
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

	public Point getRelMagPt(Convert convert){
		int x = (int)getRefX();
		int y = (int)getRefY();
		for (int i = 0; i < armAngles.length; i++){
			x = x + (int)(convert.scale(getWidth()) * Math.cos(armAngles[i]));
			y = y + (int)(convert.scale(getHeight()) * Math.sin(armAngles[i]));
		}
		x -= convert.scale(getWidth() +this.getWidth()/8);
		y -= convert.scale(getHeight()+this.getHeight()/2);
		return new Point(x,y);
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

	public Point getMagnetTip(){
		return magnetTip;
	}

	public Point getMagnetTipAccept(){
		return magnetTipAccept;
	}

	public void draw(Graphics2D g2d, Convert convert){
		AffineTransform before = g2d.getTransform();
		Point pt, pivot;

		for (int i=0; i < getNumComp(); i++){
			pt = convert.toCanvas(getPoint(i));
			pivot = convert.toCanvas(getPivot(i));
			Dimension dim = convert.scaleDim(this);
			Draw.point(g2d, pivot,10);
			Draw.rotate(g2d, armAngles[i], pivot); 
			Draw.drawRect(g2d, pt, dim);
		}
		pt = convert.toCanvas(getMagnetPoint());
		Draw.drawRect(g2d, pt, convert.scaleDim(mdim));

		Point tt = new Point((int)(pt.x + convert.scale(mdim.getWidth())), (int)(pt.y + convert.scale(mdim.getHeight() / 2)));
		magnetTipAccept = new Point((int)(tt.x + convert.scale(20)), (int)tt.y);
		g2d.getTransform().transform(tt,tt);
		g2d.getTransform().transform(magnetTipAccept,magnetTipAccept);
		magnetTip = convert.fromCanvas(tt);
		magnetTipAccept = convert.fromCanvas(magnetTipAccept);

System.out.println(pickup!=null);
		if (pickup!=null){
			pickup.setPtRef((int)magnetTip.x, (int)magnetTip.y);
			pickup.setTransform(g2d.getTransform());
		}
/*
		tt = convert.toCanvas(tt);
		magnetTip = convert.toCanvas(magnetTip);
*/

		g2d.setTransform(before);
g2d.setColor(Color.BLUE);
Draw.point(g2d,convert.toCanvas(magnetTip),10);
Draw.point(g2d,convert.toCanvas(magnetTipAccept),10);
g2d.setColor(Color.RED);
//Draw.point(g2d,(magnetTip),10);
//Draw.point(g2d,(magnetTipAccept),10);
	}
}
