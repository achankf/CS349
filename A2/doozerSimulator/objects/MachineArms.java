package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.*;
import java.util.Random;

public final class MachineArms extends BaseComponent{
	private double [] armAngles;
	Dimension mdim;
	Point magnetTip, magnetTipAccept;

	public MachineArms(Point ptRef, int width, int height, double [] providedAngles){
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
		double pi2 = Math.PI/2;
		if (armAngles[i] > pi2){
			armAngles[i] = pi2;
		} else if (armAngles[i] < -pi2){
			armAngles[i] = -pi2;
		}
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
		colorPicker.setSeed(seed);

		for (int i=0; i < getNumComp(); i++){
			pt = convert.toCanvas(getPoint(i));
			pivot = convert.toCanvas(getPivot(i));
			Dimension dim = convert.scaleDim(this);
			Draw.rotate(g2d, armAngles[i], pivot); 
			Draw.drawRect(g2d, pt, dim,colorPicker);
			Draw.point(g2d, pivot, (int)convert.scale(Config.RENDER_POINT_SIZE));
		}
		pt = convert.toCanvas(getMagnetPoint());
		Draw.drawRect(g2d, pt, convert.scaleDim(mdim),colorPicker);

		Point magnetTip = new Point((int)(pt.x + convert.scale(mdim.getWidth())), (int)(pt.y + convert.scale(mdim.getHeight() / 2)));
		magnetTipAccept = new Point((int)(magnetTip.x + convert.scale(20)), (int)magnetTip.y);

		g2d.getTransform().transform(magnetTip,magnetTip);
		g2d.getTransform().transform(magnetTipAccept,magnetTipAccept);
		magnetTip = convert.fromCanvas(magnetTip);
		magnetTipAccept = convert.fromCanvas(magnetTipAccept);

		if (pickup!=null){
			pickup.setPtRef((int)magnetTip.x, (int)magnetTip.y);
		}

		g2d.setTransform(before);
		if (pickup == null){
			g2d.setColor(Color.BLUE);
			Draw.point(g2d,convert.toCanvas(magnetTipAccept), (int)convert.scale(Config.RENDER_POINT_SIZE));
		}
	}
}
