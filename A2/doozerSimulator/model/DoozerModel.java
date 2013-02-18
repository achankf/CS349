package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import doozerSimulator.objects.*;
import doozerSimulator.*;
import doozerSimulator.view.*;

public class DoozerModel extends BaseModel{
	protected ArrayList <GameObject> objectList;
	private Doozer doozer;
	private DoozerArms arm;
	private DoozerBody body;
	private CandyFactory factory;

	public DoozerModel(){
		objectList = new ArrayList <GameObject>();
		double [] angles = {-1.0471975,0.4,0.4,0.1};

		// create the doozer
		Point doozerLoc = new Point(200,80);
		doozer = new Doozer();
		body = new DoozerBody(doozerLoc,
			(int)Config.DOOZER_BODY_DIM.getWidth(),
			(int)Config.DOOZER_BODY_DIM.getHeight());
		doozer.addComp(body);
		arm = new DoozerArms(doozerLoc,
			(int)Config.DOOZER_ARM_DIM.getWidth(),
			(int)Config.DOOZER_ARM_DIM.getHeight(),
			angles);
		doozer.addComp(arm);

		// create candies
		factory = new CandyFactory();
		factory.massProduceCandy(Config.NUM_CANDIES);

		objectList.add(factory);
		objectList.add(doozer);
	}

	public void drawAll(Graphics2D g2d, Convert convert){
		for (GameObject go : objectList){
			go.drawAll(g2d, convert);
		}
	}

	public Selected containsAll(Point pt){
		Selected ret = null;
		for (int i = objectList.size() -1 ; i >= 0; i--){
			ret = objectList.get(i).containsAll(pt);
			if (ret != null) return ret;
		}
/*
		for (GameObject go : objectList){
			ret = go.containsAll(pt);
			if (ret != null) return ret;
		}
*/
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

	public void update(){
		for (GameObject go : objectList){
			go.update();
		}
	}
}
