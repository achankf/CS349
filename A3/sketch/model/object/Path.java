package sketch.model.object;

import sketch.*;
import java.awt.*;
import java.util.*;

public class Path{
	protected Point centre;
	protected TreeMap<Integer, Point> tree;
	protected int existFrom = 0, existTo = -1;

	public Path(Path path){
		centre = new Point(path.centre);
		tree = new TreeMap<Integer, Point>(path.tree);
	}

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
 		this.tree = new TreeMap<Integer, Point>();
	}

	public void addDelta(int time, Point delta){
		tree.tailMap(time).clear();
		//Integer temp = tree.lowerKey(time);
		Map.Entry<Integer,Point> me = tree.lowerEntry(time);

		if (me != null){
			tree.put(time, PointTools.ptSum(me.getValue(), delta));
		} else {
			tree.put(time, delta);
		}
	}

	public void draw(Graphics2D g2d, int frame){
		if (tree.isEmpty()) return;
		Point prev = new Point(centre);
		for (Point delta : tree.values()){
			Point cur = PointTools.ptSum(delta, centre);
			g2d.drawLine((int)prev.x, (int)prev.y, (int)cur.x, (int)cur.y);
			prev = cur;
		}
	}

	public Point getDelta(int frame){
		Map.Entry<Integer,Point> me = tree.lowerEntry(frame);
		if(me != null){
			return new Point(me.getValue());
		} else {
			return new Point(0,0);
		}
	}
}
