package sketch.model.object;

import sketch.*;
import java.awt.*;
import java.util.*;

public class Path{
	protected Point centre;
	protected TreeMap<Integer, Point> tree = new TreeMap<Integer, Point>();
	protected int existFrom = 0, existTo = -1;

	public Point getPoint(int idx){
		return tree.get(idx);
	}

	public void print(){
		for (Object obj : tree.entrySet()){
			System.out.println(obj);
		}
	}

	public Path(Point centre){
		this.centre = centre;
	}

	public void addDelta(int time, Point delta){
		tree.tailMap(time).clear();
		Integer temp = tree.lowerKey(time);
		tree.put(time,delta);
	}

	public void draw(Graphics2D g2d, int frame){
		if (tree.isEmpty()) return;
		Point prev = new Point(centre);
		for (Point delta : tree.values()){
			Point cur = PointTools.ptSum(delta, prev);
			g2d.drawLine((int)prev.x, (int)prev.y, (int)cur.x, (int)cur.y);
			prev = cur;
		}
	}

	public Point getDelta(int frame){
		double deltax = 0, deltay = 0;
		for (Point temp : tree.headMap(frame).values()){
			deltax += temp.getX();
			deltay += temp.getY();
		}
		return new Point((int)deltax, (int)deltay);
	}
}
