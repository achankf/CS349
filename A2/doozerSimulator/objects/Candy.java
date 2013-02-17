package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import doozerSimulator.Draw;

public final class Candy extends BaseComponent{
	public Candy(Point ptRef, int width, int height){
		super(ptRef,width,height);
	}

	public void move(int i, Point pt){
	}

	public int getNumComp(){
		return 1;
	}

	public void draw(Graphics2D g2d, Convert convert){
		Point coor = convert.toCanvas(getPoint(0));
		Dimension dim = convert.scaleDim(this);
		Draw.drawRect(g2d,coor,dim);
	}

	@Override
	public void drawPicked(Graphics2D g2d, Convert convert){
		Point coor = convert.toCanvas(getPoint(0));
		Dimension dim = convert.scaleDim(new Dimension((int)this.getHeight(), (int)this.getWidth()));
		Draw.drawRect(g2d,coor,dim);
	}
}
