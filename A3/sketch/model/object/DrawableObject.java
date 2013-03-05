package sketch.model.object;

import sketch.Config;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class DrawableObject extends Collector{
	protected Boolean highlighted = false;
	protected Path path = null;

	public void setHighlighted(Boolean b){
		this.highlighted = b;
	}

	public void draw(Graphics2D g2d, int frame){
		if (lst.isEmpty()) return;
		Point prev = lst.get(0);
		for (int i = 1; i < lst.size(); i++){
			Point cur = lst.get(i);
			g2d.drawLine((int)prev.x, (int)prev.y, (int)cur.x, (int)cur.y);
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

	public void setPath(Path path){
		this.path = path;
	}

	public Path getPath(){
		return path;
	}
}
