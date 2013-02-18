package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.*;
import doozerSimulator.view.*;
import java.util.Random;

public final class Candy extends BaseComponent{
	int seed;

	public Candy(Point ptRef, int width, int height){
		super(ptRef,width,height);
		Random r = new Random();
		seed = r.nextInt();
		trans = new AffineTransform();
	}

	public void move(int i, Point pt, AffineTransform at){
	}

	public int getNumComp(){
		return 1;
	}

	public void draw(Graphics2D g2d, Convert convert){
		Point coor = convert.toCanvas(getPoint(0));
		Dimension dim = convert.scaleDim(this);
		colorPicker.setSeed(seed);
		Draw.drawRect(g2d,coor,dim,colorPicker);
	}

	@Override
	public void update(){
		double x = getRefX();
		double y = getRefY();
		if (x + getWidth() > Config.DEFAULT_DIM.getWidth()){
			x = Config.DEFAULT_DIM.getWidth() - getWidth();
		}
		if (y - getHeight() > Config.LAND_HEIGHT){
			if (y - Config.FALL_SPEED > 0)
				setPtRef((int)x,(int)(y - Config.FALL_SPEED));
			else 
				setPtRef((int)x,(int)(0));
		}
	}
}
