package doozerSimulator;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Random;
import java.awt.Color;

public final class Draw{
	public static void drawRect(Graphics2D g2d, Point coor, Dimension dim, Random r){
		g2d.setColor(getColor(r));
		g2d.fillRect((int)coor.getX(),(int)coor.getY(),(int)dim.getWidth(),(int)dim.getHeight());
	}

	public static Color getColor(Random r){
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}

	public static void rotate(Graphics2D g2d, double theta, Point pt){
		g2d.rotate(theta, pt.x, pt.y);
	}

	public static void point(Graphics2D g2d, Point pt, int radius){
		int half = radius / 2;
		g2d.setColor(Color.BLACK);
		g2d.fillOval((int)pt.x - half, (int)pt.y - half, radius, radius);
	}
};
