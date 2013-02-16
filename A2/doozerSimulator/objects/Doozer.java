package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.*;

final public class Doozer extends GameObject{
	private double [] armAngles;
	private int armWidth, armHeight;

	public Doozer(int x, int y, int armWidth, int armHeight, double [] providedAngles){
		super(x,y);
		this.armHeight = armHeight;
		this.armWidth = armWidth;
		armAngles = new double [providedAngles.length];
		System.arraycopy(providedAngles,0,armAngles,0, providedAngles.length);
	}

	public void setAngle(int i, double theta){
		armAngles[i] = theta;
	}

	public double getAngle(int i){
		return armAngles[i];
	}

	public int getNumArms(){
		return armAngles.length;
	}

	public double getArmX(int i){
		return getX() + armWidth *i - i * armWidth/8;
	}

	public double getArmY(int i){
		return getY();
	}

	public double getPivotX(int i){
		return getX() + armWidth*i - i*armWidth/8;
	}

	public double getPivotY(int i){
		return getY() - armHeight / 2;
	}

	public Point componentPos(int i, Point pt){
		double x = pt.x - getPivotX(i);
		double y = pt.y - getPivotY(i);
		return new Point((int)x,(int)y);
	}

	public Point getPivot(int i){
		return new Point((int)getPivotX(i),(int)getPivotY(i));
	}

	public int getArmWidth(){
		return armWidth;
	}

	public int getArmHeight(){
		return armHeight;
	}

	public Boolean contains(int i, Point pt){
		return pt.getX() >= getArmX(i) && pt.getX() <= getArmX(i) + getArmWidth()
		&& pt.getY() <= getArmY(i) && pt.getY() >= getArmY(i) - getArmHeight();
	}
}
