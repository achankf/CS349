package doozerSimulator.objects;

import java.awt.Point;

public class Selected{
	public BaseComponent comp;
	public int i;
	public Point pt, pivot;
	public double angle;
	public Selected(BaseComponent comp, int i, Point pt, Point pivot, double angle){
		this.i = i;
		this.comp = comp;
		this.pt = pt;
		this.pivot = pivot;
		this.angle = angle;
	}
}
