package sketch.model;

import java.util.LinkedList;
import sketch.model.object.*;
import sketch.Config;

public final class SketchModel extends BaseModel{
	private LinkedList<DrawableObject> objList = new LinkedList<DrawableObject>();
	private int frame = 0;

	public void addObject(DrawableObject obj){
		objList.add(obj);
		resetAllViews();
	}

	public void removeObject(DrawableObject obj){
		objList.remove(obj);
		resetAllViews();
	}

	public LinkedList<DrawableObject> getObjLst(){
		return objList;
	}

	public void setFrame(int frame){
		this.frame = frame;
	}
	
	public int getFrame(){
		return this.frame;
	}
}
