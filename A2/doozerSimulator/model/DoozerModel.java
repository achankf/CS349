package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class DoozerModel extends BaseModel{
	DoozerNode root;
	Point firstPivot;

	public DoozerModel(){
		firstPivot = new Point(0,250);
		this.root = new DoozerNode(180,250,200,50);
		DoozerNode nextCrane = new DoozerNode(340,250,200,50);
		this.root.addChild(nextCrane);
		DoozerNode nextNextCrane = new DoozerNode(500,250,200,50);
		root.setAngle(-1.0471975);
		nextCrane.setAngle(0.4);
		nextNextCrane.setAngle(0.4);
		nextCrane.addChild(nextNextCrane);
		//recalculateAngle();
	}

	public void recalculateAngle(){
		LinkedList<DoozerNode> nodeList = new LinkedList<DoozerNode>();
		Point prevPivot = getFirstPivot();
		nodeList.add(getRoot());
		while(!nodeList.isEmpty()){
			DoozerNode node = nodeList.removeFirst();
			Point pivot = node.getPivot();
			double vx = pivot.x-prevPivot.x;
			double vy = pivot.y-prevPivot.y;
			double angle = java.lang.Math.acos(vx / java.lang.Math.sqrt(java.lang.Math.pow(vx,2) + java.lang.Math.pow(vy,2)));
System.out.println("Angle:"+angle);
			node.setAngle(angle);
			prevPivot = pivot;
			for(DoozerNode dn : node.getChildren()){
				nodeList.add(dn);
			}
		}
	}

	public DoozerNode getRoot(){
		return this.root;
	}

	public Point getFirstPivot(){
		return firstPivot;
	}
}
