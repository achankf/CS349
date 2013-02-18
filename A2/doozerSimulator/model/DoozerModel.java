package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import doozerSimulator.objects.*;
import doozerSimulator.view.*;

public class DoozerModel extends BaseModel{
	protected ArrayList <GameObject> objectList;
	private DoozerArms arm;
	private DoozerBody body;
	private CandyFactory factory;

	public DoozerModel(){
		objectList = new ArrayList <GameObject>();
		double [] angles = {-1.0471975,0.4,0.4,0.1};

		// create the doozer
		{
			Point doozerLoc = new Point(200,80);
			Doozer doozer = new Doozer();
			body = new DoozerBody(doozerLoc,200,100);
			doozer.addComp(body);
			arm = new DoozerArms(doozerLoc,200,20,angles);
			doozer.addComp(arm);
			objectList.add(doozer);
		}

		// create candies
		{
			Point pt = new Point(500,100);
			factory = new CandyFactory();
			factory.addComp(new Candy(pt, 50, 100));
			pt = new Point(700,200);
			factory.addComp(new Candy(pt, 100, 200));
			pt = new Point(600,50);
			factory.addComp(new Candy(pt, 100, 50));
			objectList.add(factory);
		}
	}

	public void drawAll(Graphics2D g2d, Convert convert){
		for (GameObject go : objectList){
			go.drawAll(g2d, convert);
		}
	}

	public Selected containsAll(Point pt){
		Selected ret = null;
		for (GameObject go : objectList){
			ret = go.containsAll(pt);
			if (ret != null) return ret;
		}
		return ret;
	}

	public void pickUp(BaseComponent c){
		arm.setPickUp(c);
		body.setPickUp(c);
		factory.setPickUp((Candy)c);
	}

	public void findPickUp(){
		if (arm.getPickUp() != null) return;
		pickUp(factory.findPickUp(arm.getMagnetTipAccept(),arm.getTransform()));
	}

	public BaseComponent getFirst(){
		return factory.first();
	}

	public Point getMagnetTip(){
		return arm.getMagnetTip();
	}

	public void update(DoozerView view){
		for (GameObject go : objectList){
			go.update(view);
		}
	}
}
