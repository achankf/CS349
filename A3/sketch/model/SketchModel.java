package sketch.model;

import java.util.LinkedList;
import sketch.model.object.*;
import sketch.Config;

public class SketchModel extends BaseModel{
	protected int timeCurrent = 0, timeLast = Config.TIMELAST;
	protected LinkedList<DrawableObject> objList = new LinkedList<DrawableObject>();
	protected LinkedList<Path> pathList= new LinkedList<Path>();

	public void addObject(DrawableObject obj){
		objList.add(obj);
		updateAllViews();
	}

	public void removeObject(DrawableObject obj){
		objList.remove(obj);
		updateAllViews();
	}

	public LinkedList<DrawableObject> getObjLst(){
		return objList;
	}
}
