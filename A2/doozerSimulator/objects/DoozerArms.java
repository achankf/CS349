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
		armAngles = providedAngles;
//		armAngles = new double [providedAngles.length];
//		System.arraycopy(providedAngles,0,armAngles,0, providedAngles.length);
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
		AffineTransform trans = new AffineTransform();
		for (int j = 0; j < i; j++){
			rotatePoint(pt, getPivot(j),armAngles[j]);
		}
		armAngles[i] = calculateAngle(componentPos(i,pt));
/*
		for (int j = 0; j < armAngles.length; j++){
			Point pivot = getPivot(j);
			trans.rotate(armAngles[j],pivot.x, pivot.y);
		}
		if (pickup != null){
			pt = pickup.getPoint(0);
			//trans.transform(pt,pt);
			//pickup.setPtRef(pt.x,pt.y);
		}
*/
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
		AffineTransform [] inverse = new AffineTransform [armAngles.length];
		AffineTransform [] transs = new AffineTransform [armAngles.length];
		Point pt;
		Point pivot;
		int i;

		if (pickup != null){
			Point temp = pickup.getPoint(0);
			for (i = 0; i < getNumComp(); i++){
				pivot = new Point(getPivot(i));
				rotatePoint(temp, pivot, -armAngles[i]);
			}
			Point point = pickup.getPoint(0);
			g2d.getTransform().transform(point,point);
			//Draw.point(g2d,convert.toCanvas(point),20);
			Draw.point(g2d,convert.toCanvas(temp),20);
			//pickup.setPtRef(point.x,point.y);
			pickup.drawPicked(g2d,convert);
		}

		
		for (i = 0; i < getNumComp(); i++){
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

			transs[i] = g2d.getTransform();
			transs[i].rotate(-armAngles[i], pivot.x,pivot.y); 
			

			Draw.rotate(g2d, armAngles[i], pivot); 

			//transs[i] = g2d.getTransform();

try{
inverse[i] = g2d.getTransform().createInverse();
			pivot = (getPivot(i));
			Point tempp = convert.toCanvas(getPoint(i));
			g2d.getTransform().inverseTransform(tempp,tempp);
			Draw.point(g2d, tempp,10);

			tempp = convert.toCanvas(getPoint(i));
//			g2d.getTransform().transform(tempp,tempp);
g2d.setColor(Color.RED);
			Draw.point(g2d, tempp,10);
g2d.setColor(Color.BLACK);
} catch (Exception e){

}
			Draw.drawRect(g2d, pt, dim);
		}
		pt = convert.toCanvas(getMagnetPoint());
		Draw.drawRect(g2d, pt, convert.scaleDim(mdim));
		Draw.point(g2d,pt,10);
		Draw.point(g2d, (new Point(pt.x+((int)convert.scale(10)), pt.y+((int)convert.scale(100)))),10);

		if (pickup != null){
			Point temp = pickup.getPoint(0);
			for (i=0; i < getNumComp(); i++){
				pivot = new Point(getPivot(i));
				rotatePoint(temp, pivot, -armAngles[i]);
			}
			Point point = pickup.getPoint(0);
			g2d.getTransform().transform(point,point);
			//Draw.point(g2d,convert.toCanvas(point),20);
			//pickup.setPtRef(point.x,point.y);
			Draw.point(g2d,convert.toCanvas(temp),20);
			pickup.drawPicked(g2d,convert);
		}

		//Draw.drawRect(g2d, convert.toCanvas((new Point(500,100))), convert.scaleDim(new Dimension(30,100)));
		g2d.setTransform(before);
if (pickup!=null){
			pickup.drawPicked(g2d,convert);
	g2d.setColor(Color.YELLOW);
	for(i = 0; i < inverse.length; i++){
		Point tempp = convert.toCanvas(pickup.getPoint(i));
		Point tempp2 = convert.toCanvas(getPivot(i));
		transs[i].transform(tempp,tempp);
		transs[i].transform(tempp2,tempp2);
		Draw.point(g2d,(tempp),15);
		Draw.point(g2d,(tempp2),15);
	}
	pickup.setTransform(transs[i-1]);
	g2d.setColor(Color.BLACK);
}

	}

	public void pickUp(BaseComponent c){
		pickup = c;
	}
}
