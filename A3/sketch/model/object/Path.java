package sketch.model.object;

import sketch.*;
import java.awt.*;
import java.util.ArrayList;

public class Path extends Collector{
	protected Point centre;
	protected Point prev;

	public Path(Point centre){
		this.centre = centre;
		this.prev = centre;
	}

	@Override
	public void addPoint(Point pt){
		Point temp = PointTools.ptDiff(pt, prev);
		prev = new Point(pt);
		super.addPoint(temp);
	}

	public void draw(Graphics2D g2d, int frame){
		if (lst.isEmpty()) return;
		Point prev = new Point(centre);
		for (int i = 1; i < lst.size(); i++){
			Point cur = PointTools.ptSum(lst.get(i), prev);
			g2d.drawLine((int)prev.x, (int)prev.y, (int)cur.x, (int)cur.y);
			prev = cur;
		}
	}
}
