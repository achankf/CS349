package doozerSimulator.objects;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import java.awt.Graphics2D;
import doozerSimulator.*;
import java.awt.Color;

public final class MachineBody extends BaseComponent{

	public MachineBody(Point ptRef, int width, int height){
		super(ptRef,width,height);
	}

	public void move(int i, Point pt, AffineTransform at){
		if (pickup!=null){
			pickup.setPtRef((int)(pickup.getX(0) + pt.x - getRefX()), (int)(pickup.getY(0)));
		}
		setPtRef(pt.x, (int)getRefY());
	}

	@Override
	public double getX(int i){
		return this.getRefX() - this.getWidth()/2;
	}

	@Override
	public double getY(int i){
		return this.getRefY() + this.getHeight() / 5;
	}

	public int getNumComp(){
		return 1;
	}

	public void draw(Graphics2D g2d, Convert convert){
		colorPicker.setSeed(seed);
		double x = getX(0);
		if (x < 0){
			setPtRef((int)(0 + this.getWidth() / 2),(int)getRefY());
		} else if (x + getWidth() > Config.DEFAULT_DIM.getWidth()){
			setPtRef((int)(Config.DEFAULT_DIM.getWidth() - this.getWidth()),(int)getRefY());
		}
		Point coor = convert.toCanvas(getPoint(0));
		Dimension dim = convert.scaleDim(this);
		g2d.setColor(Color.BLUE);
		Draw.drawRect(g2d,coor,dim);
	}
}
