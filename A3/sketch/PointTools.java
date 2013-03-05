package sketch;

import java.awt.Point;

public class PointTools{
	public static Point ptDiff(Point pt1, Point pt2){
		return new Point(
			(int)(pt1.getX() - pt2.getX()),
			(int)(pt1.getY() - pt2.getY()));
	}

	public static Point ptSum(Point pt1, Point pt2){
		return new Point(
			(int)(pt1.getX() + pt2.getX()),
			(int)(pt1.getY() + pt2.getY()));
	}
}
