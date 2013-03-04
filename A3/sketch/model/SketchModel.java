package sketch.model;

import java.util.LinkedList;
import sketch.model.object.*;
import sketch.Config;

public class SketchModel extends BaseModel{
	protected int timeCurrent, timeLast;
	protected LinkedList<DrawableObject> objList;

	public SketchModel(){
		this.timeCurrent = 0;
		this.timeLast = Config.TIMELAST;
	}

	public void addObject(DrawableObject obj){
		objList.add(obj);
	}
	public void removeObject(DrawableObject obj){
		objList.remove(obj);
	}
}
