package sketch.model.object;

import sketch.*;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.*;
import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class DrawableObject{
	protected Path path = null;
	protected ArrayList<Point> lst = new ArrayList<Point>(Config.COLLECTOR_MIN);
	protected int existFrom = 0, existTo = -1;

	public DrawableObject(int existFrom){
		this.existFrom = existFrom;
	}

	public DrawableObject(Element ele) throws Exception{
			existFrom = Integer.parseInt(XMLTools.extractKVP(ele,"exist_from"));
			existTo = Integer.parseInt(XMLTools.extractKVP(ele,"exist_to"));
		
			NodeList pts = ele.getElementsByTagName("pt");
			for (int j = 0; j < pts.getLength(); j++){
				Element pt = (Element) pts.item(j);
				Point temp = XMLTools.makePoint(pt);
				addPoint(XMLTools.makePoint(pt));
			}

			NodeList pathlst = ele.getElementsByTagName("path");
			if (pathlst.getLength() == 0) return;
			Element pathele = (Element)pathlst.item(0);
			path = new Path(pathele);
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

	public void write(Document doc, Element ele) throws IOException{
		XMLTools.addPair(doc,ele,"exist_from", existFrom);
		XMLTools.addPair(doc,ele,"exist_to", existTo);
		for (Point pt : lst){
			XMLTools.appendPoint(doc,XMLTools.nextLevel(doc, ele, "pt"),pt);
		}

		if (path == null) return;
		Element next = XMLTools.nextLevel(doc, ele, "path");
		path.write(doc,next);
	}
}
