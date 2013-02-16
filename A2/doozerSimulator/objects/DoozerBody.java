package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import doozerSimulator.Draw;

public final class DoozerBody extends BaseComponent{
	public DoozerBody(Point ptRef, int width, int height){
		super(ptRef,width,height);
	}

	public void move(int i, Point pt){
		int newPtX = (int)getRefX();
		if (pt.x > getRefX()){
			newPtX += 1;
		} else if (pt.x < getRefX()){
			newPtX -= 1;
		}
		setPtRef(newPtX, (int)getRefY());
	}

	public double getX(int i){
		return this.getRefX() - this.getWidth()/2;
	}

	public double getY(int i){
		return this.getRefY() + this.getHeight() / 5;
	}

	public int getNumComp(){
		return 1;
	}

	public void draw(Graphics2D g2d, Convert convert){
		Point coor = convert.toCanvas(getPoint(0));
		Dimension dim = convert.scaleDim(this);
		Draw.drawRect(g2d,coor,dim);
	}
}
