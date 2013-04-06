package chan.alfred.sketchplayer;

import android.graphics.Point;
import java.io.*;

public class PointTools{
	public static Point ptDiff(Point pt1, Point pt2){
		if (pt1 == null) return pt2;
		if (pt2 == null) return pt1;
		return new Point(
			(int)(pt1.x - pt2.x),
			(int)(pt1.y - pt2.y));
	}

	public static Point ptSum(Point pt1, Point pt2){
		if (pt1 == null) return pt2;
		if (pt2 == null) return pt1;
		return new Point(
			(int)(pt1.x + pt2.x),
			(int)(pt1.y + pt2.y));
	}

	public static void writeToFile(DataOutputStream out, Point pt) throws IOException{
			out.writeInt((int)pt.x);
			out.writeInt((int)pt.y);
	}

	public static Point readFromFile(DataInputStream in) throws IOException{
		int x = in.readInt();
		int y = in.readInt();
		return new Point(x,y);
	}
}
