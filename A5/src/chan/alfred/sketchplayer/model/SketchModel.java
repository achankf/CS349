package chan.alfred.sketchplayer.model;

import java.util.LinkedList;

import android.graphics.Point;
import chan.alfred.sketchplayer.model.object.*;
import chan.alfred.sketchplayer.Config;

import java.io.*;

public final class SketchModel extends BaseModel{
	private LinkedList<DrawableObject> objList = new LinkedList<DrawableObject>();
	private int maxFrame = Config.SLIDER_MAX;
	private int frame = 0;
	
	public SketchModel(){
        DrawableObject draw = new DrawableObject(0);
        draw.addPoint(new Point(50,10));
        draw.addPoint(new Point(50,30));
        draw.addPoint(new Point(80,30));
        draw.addPoint(new Point(80,10));
        draw.addPoint(new Point(50,10));
        addObject(draw);
	}

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

	public void write(DataOutputStream out) throws IOException{
		out.writeInt(maxFrame);
		out.writeInt(objList.size());
		for (DrawableObject obj : objList){
			obj.write(out);
		}
	}

	public void read(DataInputStream in) throws IOException{
		LinkedList<DrawableObject> newObjList = new LinkedList<DrawableObject>();

		int newMaxFrame = in.readInt();
		int size = in.readInt();
		for (int i = 0; i < size; i++){
			newObjList.add(new DrawableObject(in));
		}

		this.objList = newObjList;
		frame = 0;
		maxFrame = newMaxFrame;
	}

	public void setMaxFrame(int val){
		maxFrame = val;
	}

	public int getMaxFrame(){
		return maxFrame;
	}
}
