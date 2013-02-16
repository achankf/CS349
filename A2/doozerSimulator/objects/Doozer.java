package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.*;

final public class Doozer extends GameObject{

	public Doozer(int x, int y, int armWidth, int armHeight, int bodyWidth, int bodyHeight, double [] providedAngles){
		super(x,y);
		compList = new ArrayList<BaseComponent>();
		this.compList.add(new DoozerBody(this,bodyWidth,bodyHeight));
		this.compList.add(new DoozerArms(this,armWidth,armHeight,providedAngles));
	}

	public void drawAll(Graphics2D g2d, Convert convert){
		for (BaseComponent bo : compList){
			bo.draw(g2d,convert);
		}
	}
}
