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
	BaseComponent pickup = null;

	public DoozerArms(Point ptRef, int width, int height, double [] providedAngles){
		super(ptRef,width,height);
		armAngles = new double [providedAngles.length];
		System.arraycopy(providedAngles,0,armAngles,0, providedAngles.length);
		mdim = new Dimension(width/15,(int)(height*2));
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

	public Point findPickUp(Point pt){
		Point temp = new Point(pt);
		int i = 0;
		for (; i < getNumComp(); i++){
			Point pivot = new Point(getPivot(i));
			rotatePoint(temp, pivot, armAngles[i]);
		}
System.out.println(temp);
		return temp;
	}

	public void move(int i, Point pt){
			for (int j = 0; j < i; j++){
				rotatePoint(pt, getPivot(j),armAngles[j]);
			}
			armAngles[i] = calculateAngle(componentPos(i,pt));
			if (pickup != null){
//				pickup.setPtRef(pt.x,pt.y);
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

	public void draw(Graphics2D g2d, Convert convert){
		AffineTransform before = g2d.getTransform();
		Point pt;
		Point pivot;
		int i = 0;
Point temp = new Point(500,100);
rotateNPoint(armAngles.length, temp);
		Draw.point(g2d,convert.toCanvas(temp),10);
		g2d.setColor(Color.RED);
			Draw.drawRect(g2d, convert.toCanvas(new Point((int)temp.x, (int)(temp.y+ convert.scale(30)))), convert.scaleDim(new Dimension(100,30)));
		for (; i < getNumComp(); i++){
			pt = convert.toCanvas(getPoint(i));
			pivot = convert.toCanvas(getPivot(i));
			Dimension dim = convert.scaleDim(this);
			Draw.point(g2d, pivot,10);
			Draw.drawRect(g2d, pt, dim);
		}
		pt = convert.toCanvas(getMagnetPoint());
		Draw.drawRect(g2d, pt, convert.scaleDim(mdim));
		Draw.point(g2d,pt,10);
		g2d.setColor(Color.BLACK);

		for (i=0; i < getNumComp(); i++){
			pt = convert.toCanvas(getPoint(i));
			pivot = convert.toCanvas(getPivot(i));
			Dimension dim = convert.scaleDim(this);
			Draw.point(g2d, pivot,10);
			Draw.rotate(g2d, armAngles[i], pivot); 
			Draw.drawRect(g2d, pt, dim);
		}
		pt = convert.toCanvas(getMagnetPoint());
		Draw.drawRect(g2d, pt, convert.scaleDim(mdim));
		Draw.point(g2d,pt,10);
		Draw.point(g2d, (new Point(pt.x+((int)convert.scale(10)), pt.y+((int)convert.scale(100)))),10);

		if (pickup!=null){
			pt = convert.fromCanvas(pt);
			pickup.setPtRef(pt.x,pt.y);
			pickup.draw(g2d,convert);
		}
		//Draw.drawRect(g2d, convert.toCanvas((new Point(500,100))), convert.scaleDim(new Dimension(30,100)));
		g2d.setTransform(before);
System.out.println("PT:" + pt + " " + convert.fromCanvas(pt));
	}

	public void pickUp(BaseComponent c){
		pickup = c;
	}
}
