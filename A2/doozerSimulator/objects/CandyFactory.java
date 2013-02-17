package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.Point;

public class CandyFactory extends GameObject{
	public CandyFactory(){}

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
}
