package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class DoozerModel extends BaseModel{
	DoozerNode root;
	Point startPoint;
	final int craneLength = 200;
	final int craneWidth = 50;

	public DoozerModel(){
		startPoint = new Point(50,100);
		this.root = new DoozerNode(180,250,200,50);
		DoozerNode nextCrane = new DoozerNode(340,250,200,50);
		this.root.addNext(nextCrane);
		DoozerNode nextNextCrane = new DoozerNode(500,250,200,50);
		root.setAngle(-1.0471975);
		nextCrane.setAngle(0.4);
		nextNextCrane.setAngle(0.4);
		nextCrane.addNext(nextNextCrane);
		//recalculateAngle();
	}

	public DoozerNode getRoot(){
		return this.root;
	}

	public Point getStartPoint(){
		return startPoint;
	}

}
