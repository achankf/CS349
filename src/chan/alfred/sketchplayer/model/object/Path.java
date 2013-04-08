package chan.alfred.sketchplayer.model.object;

import chan.alfred.sketchplayer.*;
import android.graphics.Canvas;
import android.graphics.Point;
import java.util.*;
import java.io.*;

public class Path{
	protected Point centre;
	protected TreeMap<Integer, Point> tree;

	public Path(DataInputStream in, int size) throws IOException{
		tree = new TreeMap<Integer, Point>();
		centre = PointTools.readFromFile(in);
		for (int i = 0; i < size; i++){
			int frame = in.readInt();
			Point pt = PointTools.readFromFile(in);
			tree.put(frame,pt);
		}
	}

	public Path(Path path){
		centre = new Point(path.centre);
		tree = new TreeMap<Integer, Point>(path.tree);
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

	public void draw(Canvas g2d, int frame){
		if (tree.isEmpty()) return;
		Point prev = new Point(centre);
		for (Point delta : tree.values()){
			Point cur = PointTools.ptSum(delta, centre);
			g2d.drawLine(prev.x, prev.y, cur.x, cur.y, Config.DEFAULT_PAINT);
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

	public Point getPoint(int idx){
		return tree.get(idx);
	}

	public void write(DataOutputStream out) throws IOException{
		out.writeInt(tree.size());
		PointTools.writeToFile(out, centre);
		for (Map.Entry<Integer,Point> en : tree.entrySet()){
			out.writeInt(en.getKey());
			PointTools.writeToFile(out, en.getValue());
		}
	}
}
