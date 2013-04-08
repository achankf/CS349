package chan.alfred.sketchplayer;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.graphics.Point;

public class XMLTools {
	public static void addPair(Document doc, Element tar, String key,
			String value) {
		Element kNode = doc.createElement(key);
		kNode.appendChild(doc.createTextNode(value));
		tar.appendChild(kNode);
	}

	public static void addPair(Document doc, Element tar, String key, int value) {
		addPair(doc, tar, key, Integer.toString(value));
	}

	public static void appendPoint(Document doc, Element tar, Point pt) {
		addPair(doc, tar, "x", pt.x);
		addPair(doc, tar, "y", pt.y);
	}

	public static Element nextLevel(Document doc, Element tar, String str) {
		Element next = doc.createElement(str);
		tar.appendChild(next);
		return next;
	}

	public static String extractKVP(Element ele, String key) {
		NodeList nlst = ele.getElementsByTagName(key);
		Element kele = (Element) nlst.item(0);
		NodeList vlst = kele.getChildNodes();
		return vlst.item(0).getNodeValue();
	}

	public static Point makePoint(Element ele) {
		int x = Integer.parseInt(extractKVP(ele, "x"));
		int y = Integer.parseInt(extractKVP(ele, "y"));
		return new Point(x, y);
	}
}
