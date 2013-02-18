package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.*;
import doozerSimulator.view.*;

public final class Candy extends BaseComponent{

	public Candy(Point ptRef, int width, int height){
		super(ptRef,width,height);
		trans = new AffineTransform();
	}

	public void move(int i, Point pt, AffineTransform at){
	}

	public int getNumComp(){
		return 1;
	}

	public void draw(Graphics2D g2d, Convert convert){
		//AffineTransform before = g2d.getTransform();
		//g2d.setTransform(trans);
		Point coor = convert.toCanvas(getPoint(0));
		Dimension dim = convert.scaleDim(this);
		Draw.drawRect(g2d,coor,dim);
		//g2d.setTransform(before);
	}

	@Override
	public void update(DoozerView view){
		if (getRefY() - getHeight() > Config.LAND_HEIGHT){
			setPtRef((int)getRefX(),(int)(getRefY() - 1));
		}
	}
}
