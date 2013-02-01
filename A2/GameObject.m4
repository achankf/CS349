package M4_PACKAGE_NAME;

import java.awt.Polygon;
import java.awt.Point;
import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class GameObject extends Point{
	protected GameObject parent;
	protected int vectorLength;
	protected ArrayList<GameObject> children;

	public GameObject(int x, int y, int vectorLength, GameObject parent){
		super(x,y);
		this.vectorLength = vectorLength;
		this.parent = parent;
	}

	public GameObject(int x, int y, int vectorLength){
		super(x,y);
		this.vectorLength = vectorLength;
		this.parent = null;
	}

	public abstract void draw(Graphics2D g);
};
