package doozerSimulator.objects;

import java.awt.Point;
import java.awt.Dimension;

public abstract class Convert{
	private double scaleFactor;
	public Convert(double scaleFactor){
		this.scaleFactor = scaleFactor;
	}
	public abstract Point fromCanvas(Point pt);
	public abstract Point toCanvas(Point pt);
	public double scale(double val){
		return val * scaleFactor;
	}
	public Dimension scaleDim(Dimension dim){
		return new Dimension((int)scale(dim.getWidth()), (int)scale(dim.getHeight()));
	}

	public double getScale(){
		return scaleFactor;
	}
}
