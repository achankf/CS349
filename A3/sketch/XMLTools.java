package sketch;

import java.awt.Point;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;

public class XMLTools{
	public static void addPair(Document doc, Element tar, String key, String value){
		Element kNode = doc.createElement(key);
		kNode.appendChild(doc.createTextNode(value));
		tar.appendChild(kNode);
	}

	public static void addPair(Document doc, Element tar, String key, int value){
		addPair(doc, tar, key, Integer.toString(value));
	}

	public static void appendPoint(Document doc, Element tar, Point pt){
			addPair(doc, tar, "x", (int)pt.getX());
			addPair(doc, tar, "y", (int)pt.getY());
	}

	public static Element nextLevel(Document doc, Element tar, String str){
		Element next = doc.createElement(str);
		tar.appendChild(next);
		return next;
	}
}
