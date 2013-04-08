package chan.alfred.sketchplayer;

import android.graphics.Point;

public class PointTools{

	public static Point ptSum(Point pt1, Point pt2){
		if (pt1 == null) return pt2;
		if (pt2 == null) return pt1;
		return new Point(
			pt1.x + pt2.x,
			pt1.y + pt2.y);
	}
}
