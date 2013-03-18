package sketch.model.object;

import sketch.*;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.*;
import java.io.*;

public class DrawableObject{
	protected Path path = null;
	protected ArrayList<Point> lst = new ArrayList<Point>(Config.COLLECTOR_MIN);
	protected int existFrom = 0, existTo = -1;

	public DrawableObject(int existFrom){
		this.existFrom = existFrom;
	}

	public DrawableObject(DataInputStream in) throws IOException{
		existFrom = in.readInt();
		existTo = in.readInt();
		int objSize = in.readInt();
		for (int i = 0; i < objSize; i++){
			Point temp = PointTools.readFromFile(in);
			lst.add(temp);
		}

		int pathSize = in.readInt();
		if (pathSize == 0) return;
		path = new Path(in, pathSize);
	}

	public void addPoint(Point pt){
		lst.add(pt);
	}

	public void finalize(){
		lst.trimToSize();
	}

	public ArrayList<Point> getPtLst(){
		return lst;
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
	}

	public void drawPath(Graphics2D g2d, int frame){
		if (path != null){
			path.draw(g2d, frame);
		}
	}

	public Boolean containedIn(Shape p, int frame){
		Point delta = getDelta(frame);
		for (Point pt : lst){
			if(!p.contains(PointTools.ptSum(pt, delta))) return false;
		}
		return true;
	}

	public Boolean containedPartlyIn(Shape p, int frame){
		Point delta = getDelta(frame);
		for (Point pt : lst){
			if(p.contains(PointTools.ptSum(pt, delta))) return true;
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

	public Point getDelta(int frame){
		if (path == null){
			return new Point(0,0);
		}
		return path.getDelta(frame);
	}

	public void addPathDelta(int frame, Point delta){
		if (path == null) return;
		path.addDelta(frame,delta);
	}

	public void write(DataOutputStream out) throws IOException{
		out.writeInt(existFrom);
		out.writeInt(existTo);
		out.writeInt(lst.size());
		for (Point pt : lst){
			PointTools.writeToFile(out, pt);
		}
		if (path == null){
			out.writeInt(0);
		} else {
			path.write(out);
		}
	}
}
