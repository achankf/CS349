package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.*;

final public class Doozer extends GameObject{
	private double [] armAngles;
	private DoozerBody body;
/*
	private int armWidth, armHeight;
	private int bodyWidth, bodyHeight;
*/
	private Dimension armDim, bodyDim;

	public Doozer(int x, int y, int armWidth, int armHeight, int bodyWidth, int bodyHeight, double [] providedAngles){
		super(x,y);
		this.armDim = new Dimension(armWidth,armHeight);
		this.bodyDim = new Dimension(bodyWidth,bodyHeight);
		this.body = new DoozerBody(this,bodyWidth,bodyHeight);
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

	public double getBodyX(){
		return getX() -  getBodyWidth()/2;
	}

	public double getBodyY(){
		return getY() + getBodyHeight() / 5;
	}

	public double getArmX(int i){
		return getX() + getArmWidth() *i - i * getArmWidth()/8;
	}

	public double getArmY(int i){
		return getY();
	}

	public Point getArmPoint(int i){
		return new Point((int)getArmX(i),(int)getArmY(i));
	}

	public Point getBodyPoint(){
		return new Point((int)getBodyX(),(int)getBodyY());
	}

	public double getPivotX(int i){
		return getX() + getArmWidth()*i - i*getArmWidth()/8;
	}

	public double getPivotY(int i){
		return getY() - getArmHeight() / 2;
	}

	public Point componentPos(int i, Point pt){
		double x = pt.x - getPivotX(i);
		double y = pt.y - getPivotY(i);
		return new Point((int)x,(int)y);
	}

	public Point getPivot(int i){
		return new Point((int)getPivotX(i),(int)getPivotY(i));
	}

	public double getBodyWidth(){
		return bodyDim.getWidth();
	}

	public double getBodyHeight(){
		return bodyDim.getHeight();
	}

	public double getArmWidth(){
		return armDim.getWidth();
	}

	public double getArmHeight(){
		return armDim.getHeight();
	}

	public Dimension getArmDim(){
		return armDim;
	}

	public Dimension getBodyDim(){
		return bodyDim;
	}

	public Boolean contains(Point pt, Point tar, Dimension tarDim){
		return pt.getX() >= tar.getX() && pt.getX() <= tar.getX() + tarDim.getWidth()
			&& pt.getY() <= tar.getY() && pt.getY() >= tar.getY() - tarDim.getHeight();
	}

	public Boolean containsArm(int i, Point pt){
		return contains(pt, getArmPoint(i), getArmDim());
	}

	public Boolean containsBody(Point pt){
		return contains(pt, getBodyPoint(), getBodyDim());
	}

	public void moveBody(Point pt){

	}

	public void moveArm(Point pt){

	}
}
