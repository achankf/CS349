package sketch.model.object;

import sketch.Config;
import java.awt.Polygon;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DrawableObject{
	protected ArrayList<Point> ptLst = new ArrayList<Point>(Config.DRAWABLE_MIN);
	protected Boolean highlighted = false;
	protected ArrayList<Point> path = new ArrayList<Point>();

	public void addPoint(int x, int y){
		addPoint(new Point(x,y));
	}

	public void addPoint(Point pt){
		ptLst.add(pt);
	}

	public void addPathPoint(int x, int y){
		path.add(new Point(x,y));
	}

	public void setHighlighted(Boolean b){
		this.highlighted = b;
	}

	public void setPath(ArrayList<Point> path){
		this.path = path;
	}

	public void draw(Graphics2D g2d, int frame){
		if (ptLst.isEmpty()) return;
		Point prev = ptLst.get(0);
		for (int i = 1; i < ptLst.size(); i++){
			Point cur = ptLst.get(i);
			g2d.drawLine((int)prev.x, (int)prev.y, (int)cur.x, (int)cur.y);
			prev = cur;
		}
	}

	public void finalize(){
		ptLst.trimToSize();
	}

	public Boolean containedIn(Polygon p){
		for (Point pt : ptLst){
			if(!p.contains(pt)) return false;
		}
		return true;
	}
}
