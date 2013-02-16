package doozerSimulator;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Dimension;

public final class Draw{
	public static void drawRect(Graphics2D g2d, Point coor, Dimension dim){
		g2d.drawRect((int)coor.getX(),(int)coor.getY(),(int)dim.getWidth(),(int)dim.getHeight());
	}

	public static void rotate(Graphics2D g2d, double theta, Point pt){
		g2d.rotate(theta, pt.x, pt.y);
	}

	public static void point(Graphics2D g2d, Point pt, int radius){
		int half = radius / 2;
		g2d.fillOval((int)pt.x - half, (int)pt.y - half, radius, radius);
	}
};
