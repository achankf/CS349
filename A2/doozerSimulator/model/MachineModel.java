package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import doozerSimulator.objects.*;
import doozerSimulator.*;
import doozerSimulator.view.*;

public class MachineModel extends BaseModel{
	protected ArrayList <GameObject> objectList;
	private Machine doozer;
	private MachineArms arm;
	private MachineBody body;
	private CandyFactory factory;
	private Boolean gameover = false;

	private long initTime;
	private long latestTime;

	public MachineModel(long initTime){
		objectList = new ArrayList <GameObject>();
		double [] angles = {-1.0471975,0.4,0.4,0.1};
		this.initTime = initTime;
		this.latestTime = initTime;

		// create the doozer
		Point doozerLoc = new Point(200,80);
		doozer = new Machine();
		body = new MachineBody(doozerLoc,
			(int)Config.DOOZER_BODY_DIM.getWidth(),
			(int)Config.DOOZER_BODY_DIM.getHeight());
		doozer.addComp(body);
		arm = new MachineArms(doozerLoc,
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

	public void spawnCandy(int i){
		factory.massProduceCandy(i);
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

	public void update(long latestTime){
		if (gameover) return;
		this.latestTime = latestTime;
		
		for (GameObject go : objectList){
			go.update();
		}
	}

	public double getTimeElapsed(){
		return (latestTime - initTime) / 1000000000.0;
	}

	public void checkGameOver(){
		if (gameover) return;
		gameover = factory.aboveTop();
	}

	public Boolean gameOver(){
		return gameover;
	}
}
