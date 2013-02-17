package doozerSimulator.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class GameObject{
	protected ArrayList<BaseComponent> compList;

	public GameObject(){
		compList = new ArrayList<BaseComponent>();
	}

	public Selected containsAll(Point pt){
		Selected ret = null;
		for (BaseComponent bc : compList){
			ret = bc.containsAll(pt);
			if (ret != null) return ret;
		}
		return ret;
	}

	public void drawAll(Graphics2D g2d, Convert convert){
		for (BaseComponent bo : compList){
			bo.draw(g2d,convert);
		}
	}

	public void addComp(BaseComponent bc){
		compList.add(bc);
	}
}
