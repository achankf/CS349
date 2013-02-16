package doozerSimulator.objects;

import java.util.ArrayList;
import java.awt.*;

final public class Doozer extends GameObject{

	public Doozer(int x, int y){
		super(x,y);
		compList = new ArrayList<BaseComponent>();
	}

	public void drawAll(Graphics2D g2d, Convert convert){
		for (BaseComponent bo : compList){
			bo.draw(g2d,convert);
		}
	}
}
