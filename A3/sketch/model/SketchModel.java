package sketch.model;

import java.util.LinkedList;
import sketch.model.object.*;
import sketch.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

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
		XMLTools.addPair(doc,ele,"max_frame",maxFrame);
		for (DrawableObject obj : objList){
			Element next = XMLTools.nextLevel(doc, ele, "object");
			obj.write(doc, next);
		}
	}

	public void read(Document doc) throws Exception{
		NodeList nlst = doc.getElementsByTagName("max_frame");
		Element ele = (Element)nlst.item(0);
		NodeList nlst2 = ele.getChildNodes();
		int newMaxFrame = Integer.parseInt(nlst2.item(0).getNodeValue());

		NodeList nodeLst = doc.getElementsByTagName("object");
		int size = nodeLst.getLength();
		LinkedList<DrawableObject> newObjList = new LinkedList<DrawableObject>();

		for (int i = 0; i < size; i++){
			newObjList.add(new DrawableObject((Element) nodeLst.item(i)));
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
