package sketch.model.object;

import java.awt.Polygon;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DrawableObject{
	protected Polygon shape;
	protected Boolean highlighted = false;
	protected ArrayList<Point> path;

	public DrawableObject(){
		this.shape = new Polygon();
		this.path = new ArrayList<Point>();
	}

	public void addPolyPoint(int x, int y){
		shape.addPoint(x,y);
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

	public void draw(Graphics2D g2d, Point coor){
	}
}
