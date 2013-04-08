package chan.alfred.sketchplayer.model.object;

import chan.alfred.sketchplayer.*;
import android.graphics.Canvas;
import android.graphics.Point;
import java.util.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DrawableObject {
	protected int existFrom = 0, existTo = -1;
	protected ArrayList<Point> lst = new ArrayList<Point>();
	protected Path path = null;

	public DrawableObject(Element ele) throws Exception {
		existFrom = Integer.parseInt(XMLTools.extractKVP(ele, "exist_from"));
		existTo = Integer.parseInt(XMLTools.extractKVP(ele, "exist_to"));

		NodeList pts = ele.getElementsByTagName("pt");
		for (int j = 0; j < pts.getLength(); j++) {
			Element pt = (Element) pts.item(j);
			addPoint(XMLTools.makePoint(pt));
		}

		NodeList pathlst = ele.getElementsByTagName("path");
		if (pathlst.getLength() == 0)
			return;
		Element pathele = (Element) pathlst.item(0);
		path = new Path(pathele);
	}

	public DrawableObject(int existFrom) {
		this.existFrom = existFrom;
	}

	public void addPathDelta(int frame, Point delta) {
		if (path == null)
			return;
		path.addDelta(frame, delta);
	}

	public void addPoint(Point pt) {
		lst.add(pt);
	}

	public void draw(Canvas g2d, int frame) {
		if (lst.isEmpty())
			return;

		double deltax = 0, deltay = 0;
		if (path != null) {
			Point delta = path.getDelta(frame);
			deltax = delta.x;
			deltay = delta.y;
		}

		Point prev = lst.get(0);
		for (int i = 1; i < lst.size(); i++) {
			Point cur = lst.get(i);
			g2d.drawLine((int) (prev.x + deltax), (int) (prev.y + deltay),
					(int) (cur.x + deltax), (int) (cur.y + deltay),
					Config.DEFAULT_PAINT);
			prev = cur;
		}
	}

	public void drawPath(Canvas g2d, int frame) {
		if (path != null) {
			path.draw(g2d, frame);
		}
	}

	public void erasedAt(int frame) {
		existTo = frame;
	}

	public Boolean exist(int frame) {
		return frame >= existFrom && (frame < existTo || existTo == -1);
	}

	public Point getDelta(int frame) {
		if (path == null) {
			return new Point(0, 0);
		}
		return path.getDelta(frame);
	}

	public Path getPath() {
		return path;
	}

	public ArrayList<Point> getPtLst() {
		return lst;
	}

	public Boolean nonExistence() {
		return existFrom == existTo;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}
