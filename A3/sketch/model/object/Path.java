package sketch.model.object;

import sketch.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;

public class Path{
	protected Point centre;
	protected TreeMap<Integer, Point> tree;

	public Path(Path path){
		centre = new Point(path.centre);
		tree = new TreeMap<Integer, Point>(path.tree);
	}

	public Path(DataInputStream in, int size) throws IOException{
		tree = new TreeMap<Integer, Point>();
		centre = PointTools.readFromFile(in);
		for (int i = 0; i < size; i++){
			int frame = in.readInt();
			Point pt = PointTools.readFromFile(in);
			tree.put(frame,pt);
		}
	}

	public Point getPoint(int idx){
		return tree.get(idx);
	}

	public Path(Point centre){
		this.centre = centre;
 		this.tree = new TreeMap<Integer, Point>();
	}

	public void addDelta(int time, Point delta){
		tree.tailMap(time).clear();
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
		if (tree == null){
	System.out.println("ERROR");
		}
		Map.Entry<Integer,Point> me = tree.lowerEntry(frame);
		if(me != null){
			return new Point(me.getValue());
		} else {
			return new Point(0,0);
		}
	}

	public void write(Document doc, Element ele) throws IOException{
		XMLTools.addPair(doc, ele, "path_size", tree.size());
		Element centreEle = doc.createElement("centre");
		XMLTools.appendPoint(doc, centreEle, centre);
		ele.appendChild(centreEle);
		for (Map.Entry<Integer,Point> en : tree.entrySet()){
			Element next = XMLTools.nextLevel(doc, ele, "delta");
			XMLTools.addPair(doc,next,"frame", en.getKey());
			XMLTools.appendPoint(doc,next,en.getValue());
		}
	}
}
