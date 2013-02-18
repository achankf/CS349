package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import doozerSimulator.view.*;
import java.awt.Rectangle;

public class CandyFactory extends GameObject{
	protected Candy pickup = null;

	public CandyFactory(){}

	public void produceCandy(int x, int y, int width, int height){
		addComp(new Candy(new Point(x,y), width,height));
	}

	public BaseComponent findPickUp(Point pt, AffineTransform at){
		for (BaseComponent bc : compList){
			if(bc.contains(0,pt)){
				return bc;
			}
		}
		return null;
	}

	public BaseComponent first(){
		return compList.get(0);
	}

	private Boolean rectangleOverlap(BaseComponent bc1, BaseComponent bc2){
		int x1 = (int)bc1.getRefX();
		int y1 = (int)bc1.getRefY();
		int x2 = (int)bc2.getRefX();
		int y2 = (int)bc2.getRefY();
		int xx1 = x1 + (int)(bc1.getWidth());
		int yy1 = y1 - (int)(bc1.getHeight());
		int xx2 = x2 + (int)(bc2.getWidth());
		int yy2 = y2 - (int)(bc2.getHeight());

		// check candies that are above and overlap the target
		return yy1 > yy2 && (yy2 < y1 && yy1 < y2 && xx2 > x1 && xx1 > x2);
	}

	@Override
	public void update(){
		for (BaseComponent bc : compList){
			int i = 0;
			for (BaseComponent bc2 : compList){
				if (bc == bc2 || bc2 == pickup) continue;
				if (rectangleOverlap(bc,bc2)) i++;
			}
			if (i == 0) bc.update();
		}
	}

	public void setPickUp(Candy c){
		pickup = c;
	}
}
