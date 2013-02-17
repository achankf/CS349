package doozerSimulator.model;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import doozerSimulator.objects.*;

public class DoozerModel extends BaseModel{
	ArrayList <GameObject> objectList;

	public DoozerModel(){
		objectList = new ArrayList <GameObject>();

		// create the doozer
		{
			double [] angles = {-1.0471975,0.4,0.4,0.1};
			Point doozerLoc = new Point(200,100);
			Doozer doozer = new Doozer();
			doozer.addComp(new DoozerBody(doozerLoc,200,100));
			doozer.addComp(new DoozerArms(doozerLoc,200,50,angles));
			objectList.add(doozer);
		}

		// create candies
		{
			Point pt = new Point(500,100);
			CandyFactory factory = new CandyFactory();
			factory.addComp(new Candy(pt, 30, 100));
			objectList.add(factory);
		}
	}

	public void drawAll(Graphics2D g2d, Convert convert){
		for (GameObject go : objectList){
			go.drawAll(g2d, convert);
		}
	}

	public SelectedPair containsAll(Point pt){
		SelectedPair ret = null;
		for (GameObject go : objectList){
			ret = go.containsAll(pt);
			if (ret != null) return ret;
		}
		return ret;
	}
}
