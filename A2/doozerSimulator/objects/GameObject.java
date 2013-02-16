package doozerSimulator.objects;

import java.awt.Point;
import java.util.ArrayList;

public class GameObject extends Point{
	protected ArrayList<BaseComponent> compList;

	public GameObject(int x, int y){
		super(x,y);
	}

	public SelectedPair containsAll(Point pt){
		SelectedPair ret = null;
		for (BaseComponent bc : compList){
			ret = bc.containsAll(pt);
			if (ret != null) return ret;
		}
		return ret;
	}

	public void addComp(BaseComponent bc){
		compList.add(bc);
	}
}
