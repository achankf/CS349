package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import doozerSimulator.objects.*;

public class DoozerModel extends BaseModel{
	ArrayList <GameObject> objectList;
	Doozer doozer;

	public DoozerModel(){
		objectList = new ArrayList <GameObject>();
		double [] angles = {-1.0471975,0.4,0.4};
		doozer = new Doozer(100,300,200,50,angles);
		objectList.add(doozer);
	}

	public Doozer getDoozer(){
		return doozer;
	}
}
