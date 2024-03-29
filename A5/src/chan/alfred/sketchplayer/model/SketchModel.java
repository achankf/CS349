package chan.alfred.sketchplayer.model;

import java.util.LinkedList;

import chan.alfred.sketchplayer.model.object.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class SketchModel extends BaseModel {
	private int frame = 0;
	private int maxFrame = 100;
	private LinkedList<DrawableObject> objList = new LinkedList<DrawableObject>();

	public void addObject(DrawableObject obj) {
		objList.add(obj);
		resetAllViews();
	}

	public int getFrame() {
		return this.frame;
	}

	public int getMaxFrame() {
		return maxFrame;
	}

	public LinkedList<DrawableObject> getObjLst() {
		return objList;
	}

	public void read(Document doc) throws Exception {
		NodeList nlst = doc.getElementsByTagName("max_frame");
		Element ele = (Element) nlst.item(0);
		NodeList nlst2 = ele.getChildNodes();
		int newMaxFrame = Integer.parseInt(nlst2.item(0).getNodeValue());

		NodeList nodeLst = doc.getElementsByTagName("object");
		int size = nodeLst.getLength();
		LinkedList<DrawableObject> newObjList = new LinkedList<DrawableObject>();

		for (int i = 0; i < size; i++) {
			newObjList.add(new DrawableObject((Element) nodeLst.item(i)));
		}

		this.objList = newObjList;
		frame = 0;
		maxFrame = newMaxFrame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public void setMaxFrame(int val) {
		maxFrame = val;
	}
}
