package sketch.model.object;

import sketch.Config;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DrawableObject{
	protected Boolean highlighted = false;
	protected Path path = null;
	protected ArrayList<Point> lst = new ArrayList<Point>(Config.COLLECTOR_MIN);
	protected int existFrom = 0, existTo = -1;

	public DrawableObject(){}

	public DrawableObject(int existFrom){
		this.existFrom = existFrom;
	}

	public void addPoint(Point pt){
		lst.add(pt);
	}

	public Point getPoint(int idx){
		return lst.get(idx);
	}

	public void finalize(){
		lst.trimToSize();
	}

	public ArrayList<Point> getPtLst(){
		return lst;
	}

	public void print(){
		for (int i = 0; i < lst.size(); i++){
			System.out.println(i + " " + lst.get(i));
		}
	}

	public void setHighlighted(Boolean b){
		this.highlighted = b;
	}

	public void draw(Graphics2D g2d, int frame){
		if (lst.isEmpty()) return;

		double deltax = 0, deltay = 0;
		if (path != null){
			Point delta = path.getDelta(frame);
			deltax = delta.getX();
			deltay = delta.getY();
		}

		Point prev = lst.get(0);
		for (int i = 1; i < lst.size(); i++){
			Point cur = lst.get(i);
			g2d.drawLine((int)(prev.x + deltax), (int)(prev.y + deltay), (int)(cur.x + deltax), (int)(cur.y + deltay));
			prev = cur;
		}

		if (path != null){
			path.draw(g2d, frame);
		}
	}

	public Boolean containedIn(Shape p){
		for (Point pt : lst){
			if(!p.contains(pt)) return false;
		}
		return true;
	}

	public Boolean containedPartlyIn(Shape p){
		for (Point pt : lst){
			if(p.contains(pt)) return true;
		}
		return false;
	}

	public void setPath(Path path){
		this.path = path;
	}

	public Path getPath(){
		return path;
	}

	public void erasedAt(int frame){
		existTo = frame;
	}

	public Boolean exist(int frame){
		return frame >= existFrom && (frame < existTo || existTo == -1);
	}

	public Boolean nonExistence(){
		return existFrom == existTo;
	}
}
