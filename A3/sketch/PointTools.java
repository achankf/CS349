package sketch;

import java.awt.Point;
import java.io.*;

public class PointTools{
	public static Point ptDiff(Point pt1, Point pt2){
		if (pt1 == null) return pt2;
		if (pt2 == null) return pt1;
		return new Point(
			(int)(pt1.getX() - pt2.getX()),
			(int)(pt1.getY() - pt2.getY()));
	}

	public static Point ptSum(Point pt1, Point pt2){
		if (pt1 == null) return pt2;
		if (pt2 == null) return pt1;
		return new Point(
			(int)(pt1.getX() + pt2.getX()),
			(int)(pt1.getY() + pt2.getY()));
	}

	public static void writeToFile(DataOutputStream out, Point pt) throws IOException{
			out.writeInt((int)pt.getX());
			out.writeInt((int)pt.getY());
	}

	public static Point readFromFile(DataInputStream in) throws IOException{
		int x = in.readInt();
		int y = in.readInt();
		return new Point(x,y);
	}
}
