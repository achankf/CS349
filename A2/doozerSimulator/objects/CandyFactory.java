package doozerSimulator.objects;

import java.util.ArrayList;

public class CandyFactory extends GameObject{
	public CandyFactory(){}

	public void findPickUp(Selected s){
	//	System.out.println(s.i + " point:" + s.pt + " pivot:" + s.pivot + " angle:"+ s.angle);
		for (BaseComponent bc : compList){
			//System.out.println(bc.getX());
		}
	}

	public BaseComponent first(){
		return compList.get(0);
	}

	public void printall(){
		for (BaseComponent bc : compList){
			System.out.println(bc);
		}
	}
}
