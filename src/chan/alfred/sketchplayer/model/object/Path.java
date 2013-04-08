package chan.alfred.sketchplayer.model.object;

import chan.alfred.sketchplayer.*;
import android.graphics.Canvas;
import android.graphics.Point;
import java.util.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Path {
	protected Point centre;
	protected TreeMap<Integer, Point> tree;

	public Path(Element ele) throws Exception {
		tree = new TreeMap<Integer, Point>();

		Element centreEle = (Element) ele.getElementsByTagName("centre")
				.item(0);
		centre = XMLTools.makePoint(centreEle);

		NodeList deltas = ele.getElementsByTagName("delta");
		for (int i = 0; i < deltas.getLength(); i++) {
			Element delta = (Element) deltas.item(i);
			Integer frame = Integer.parseInt(XMLTools
					.extractKVP(delta, "frame"));
			Point pt = XMLTools.makePoint(delta);
			tree.put(frame, pt);
		}
	}

	public Path(Point centre) {
		this.centre = centre;
		this.tree = new TreeMap<Integer, Point>();
	}

	public void addDelta(int time, Point delta) {
		tree.tailMap(time).clear();
		Map.Entry<Integer, Point> me = tree.lowerEntry(time);

		if (me != null) {
			tree.put(time, PointTools.ptSum(me.getValue(), delta));
		} else {
			tree.put(time, delta);
		}
	}

	public void draw(Canvas g2d, int frame) {
		if (tree.isEmpty())
			return;
		Point prev = new Point(centre);
		for (Point delta : tree.values()) {
			Point cur = PointTools.ptSum(delta, centre);
			g2d.drawLine(prev.x, prev.y, cur.x, cur.y, Config.DEFAULT_PAINT);
			prev = cur;
		}
	}

	public Point getDelta(int frame) {
		if (tree == null) {
			System.out.println("ERROR");
		}
		Map.Entry<Integer, Point> me = tree.lowerEntry(frame);
		if (me != null) {
			return new Point(me.getValue());
		} else {
			return new Point(0, 0);
		}
	}

	public Point getPoint(int idx) {
		return tree.get(idx);
	}
}
