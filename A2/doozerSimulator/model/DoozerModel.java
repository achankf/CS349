package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import doozerSimulator.objects.*;

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
			Point doozerLoc = new Point(200,100);
			Doozer doozer = new Doozer();
			body = new DoozerBody(doozerLoc,200,100);
			doozer.addComp(body);
			arm = new DoozerArms(doozerLoc,200,50,angles);
			doozer.addComp(arm);
			objectList.add(doozer);
		}

		// create candies
		{
			Point pt = new Point(500,100);
			factory = new CandyFactory();
			factory.addComp(new Candy(pt, 30, 100));
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
		arm.pickUp(c);
		body.pickUp(c);
	}

	public void findPickUp(Selected s){
		Point pt = arm.getMagnetPoint();
		//arm.rotatePoint(pt);
factory.printall();
		System.out.println(arm.getMagnetPoint());
		factory.findPickUp(s);
	}

	public BaseComponent getFirst(){
		return factory.first();
	}
}
