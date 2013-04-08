package sketch.model;

import java.util.LinkedList;
import sketch.model.object.*;
import sketch.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;

public final class SketchModel extends BaseModel{
	private LinkedList<DrawableObject> objList = new LinkedList<DrawableObject>();
	private int maxFrame = Config.SLIDER_MAX;
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

	public void write(Document doc, Element ele) throws IOException{
		XMLTools.addPair(doc,ele,"max_frame", maxFrame);
		XMLTools.addPair(doc,ele,"num_objects", objList.size());

		for (DrawableObject obj : objList){
			Element next = XMLTools.nextLevel(doc, ele, "object");
			obj.write(doc, next);
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
