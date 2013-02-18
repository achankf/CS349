package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import doozerSimulator.view.*;
import java.awt.Rectangle;

public class CandyFactory extends GameObject{
	protected Candy pickup = null;

	public CandyFactory(){}

	public BaseComponent findPickUp(Point pt, AffineTransform at){
		for (BaseComponent bc : compList){
			System.out.println("BC: "+bc.getPoint(0));
			if(bc.contains(0,pt)){
				return bc;
			}
		}
		return null;
	}

	public BaseComponent first(){
		return compList.get(0);
	}

	private Boolean rectangleOverlap(BaseComponent bc1, BaseComponent bc2, DoozerView view){
		int x1 = (int)bc1.getRefX();
		int y1 = (int)bc1.getRefY();
		int x2 = (int)bc2.getRefX();
		int y2 = (int)bc2.getRefY();
		int xx1 = x1 + (int)(bc1.getWidth());
		int yy1 = y1 - (int)(bc1.getHeight());
		int xx2 = x2 + (int)(bc2.getWidth());
		int yy2 = y2 - (int)(bc2.getHeight());

		int xxx = (x1 + xx1) /2;
		return ((x1 > x2 && x1 < xx2 || xx1 > x2 && xx1 < xx2 || xxx > x2 && xxx < xx2)
			 && (yy1 < y2));
	}

	@Override
	public void update(DoozerView view){
		for (BaseComponent bc : compList){
			int i = 0;
			for (BaseComponent bc2 : compList){
				if (bc == bc2 || bc2 == pickup) continue;
				if (rectangleOverlap(bc,bc2,view)) i++;
			}
			if (i == 0) bc.update(view);
		}
	}

	public void setPickUp(Candy c){
		pickup = c;
	}
}
