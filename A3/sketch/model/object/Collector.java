package sketch.model.object;

import sketch.Config;
import java.awt.*;
import java.util.ArrayList;

public abstract class Collector{
	protected ArrayList<Point> lst = new ArrayList<Point>(Config.COLLECTOR_MIN);
	protected int existFrom = 0, existTo = -1;

	public void addPoint(Point pt){
		lst.add(pt);
	}

	public void finalize(){
		lst.trimToSize();
	}

	public ArrayList<Point> getPtLst(){
		return lst;
	}

	public void print(){
		for (Point pt : lst){
			System.out.println(pt);
		}
	}

	public abstract void draw(Graphics2D g2d, int frame);
}
