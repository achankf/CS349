package doozerSimulator.view;

import doozerSimulator.model.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.lang.*;

/**
 * A view of a right triangle that displays the triangle graphically and allows
 * the user to change the size by dragging the image with a mouse.
 */
public class DoozerView extends JComponent {
	private DoozerModel model;
	private LinkedList<Point> a,b;

	private double scale = 1.0; // how much should the triangle be scaled?
	private boolean selected = false; // did the user select the triangle to
	// resize it?

	// To format numbers consistently in the text fields.
	private static final NumberFormat formatter = NumberFormat
			.getNumberInstance();

	public DoozerView(DoozerModel model) {
		super();
		this.a = new LinkedList<Point>();
		this.b = new LinkedList<Point>();
		this.model = model;
		this.registerControllers();
		this.model.addView(new IView() {
			/** The model changed. Ask the system to repaint the triangle. */
			public void updateView() {
				repaint();
			}
		});
	}

	/** Register event Controllers for mouse clicks and motion. */
	private void registerControllers() {
		MouseInputListener mil = new MController();
		this.addMouseListener(mil);
		this.addMouseMotionListener(mil);
	}

	/** Paint the triangle, and "handles" for resizing if it was selected. */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Insets insets = this.getInsets();

		for(Point pt : a){
			g2d.fillOval(toX(pt.x)-5,toY(pt.y)-5,10,10);
		}
		for(Point pt : b){
			g2d.setColor(Color.red);
			g2d.fillOval(toX(pt.x)-5,toY(pt.y)-5,10,10);
		}
		
		LinkedList<DoozerNode> nodeList = new LinkedList<DoozerNode>();
		Point prevPivot = model.getFirstPivot();
		nodeList.add(model.getRoot());
		g2d.fillOval(toX(prevPivot.x)-5,toY(prevPivot.y)-5,10,10);
		while(!nodeList.isEmpty()){
			DoozerNode node = nodeList.removeFirst();
			Point pivot = node.getPivot();
			int x = toX(node.getX());
			int y = toY(node.getY());
			int pivotX = toX(pivot.x);
			int pivotY = toY(pivot.y);
			int prevX = toX(prevPivot.x);
			int prevY = toY(prevPivot.y);
			g2d.setColor(Color.black);
			g2d.fillOval(pivotX-5,pivotY-5,10,10);
			//g2d.rotate(angle,pivotX,pivotY); 
			g2d.setColor(Color.black);
			//g2d.drawLine(prevX,prevY,x,y);
			g2d.drawRect(x,y,node.getWidth(),node.getHeight());
			prevPivot = pivot;
			//g2d.rotate(-angle,pivotX,pivotY);
			for(DoozerNode dn : node.getChildren()){
				nodeList.add(dn);
			}
		}

		prevPivot = model.getFirstPivot();
		nodeList.add(model.getRoot());
		while(!nodeList.isEmpty()){
			DoozerNode node = nodeList.removeFirst();
			Point pivot = node.getPivot();
			int x = toX(node.getX());
			int y = toY(node.getY());
			int pivotX = toX(pivot.x);
			int pivotY = toY(pivot.y);
			int prevX = toX(prevPivot.x);
			int prevY = toY(prevPivot.y);
			g2d.setColor(Color.black);
			g2d.fillOval(pivotX-5,pivotY-5,10,10);
			g2d.rotate(node.getAngle(),pivotX,pivotY); 
			//g2d.rotate(angle,pivotX,pivotY); 
			g2d.setColor(Color.black);
			//g2d.drawLine(prevX,prevY,x,y);
			g2d.drawRect(x,y,node.getWidth(),node.getHeight());
			prevPivot = pivot;
			//g2d.rotate(-angle,pivotX,pivotY);
			for(DoozerNode dn : node.getChildren()){
				nodeList.add(dn);
			}
		}
	}

	/** Convert from the model's X coordinate to the component's X coordinate. */
	protected int toX(double modelX) {
		return (int) (modelX * this.scale) + this.getInsets().left;
	}

	/** Convert from the model's Y coordinate to the component's Y coordinate. */
	protected int toY(double modelY) {
		return this.getHeight() - (int) (modelY * this.scale) - 1
				- this.getInsets().bottom;
	}

	/** Convert from the component's X coordinate to the model's X coordinate. */
	protected double fromX(int x) {
		return (x - this.getInsets().left) / this.scale;
	}

	/** Convert from the component's Y coordinate to the model's Y coordinate. */
	protected double fromY(int y) {
		return (this.getHeight() - 1 - this.getInsets().bottom - y)
				/ this.scale;
	}

	private class MController extends MouseInputAdapter {
		/*
		 * Select or deselect the triangle.
		 */
		private boolean canDragX = false;
		private boolean canDragY = false;
		private boolean select = false;

		public Boolean contains(DoozerNode node, Point pt){
			return false;
		}

		public void rotatePoint(Point pt, Point pivot, double angle){
			double cost = Math.cos(-angle);
			double sint = Math.sin(angle);
			double tempX = fromX(pt.x) - fromX(pivot.x);
			double tempY = fromY(pt.y) - fromY(pivot.y);
			double newX = fromX(pt.x) * cost - pt.y * sint;
			double newY = fromX(pt.x) * sint + pt.y * cost;
System.out.println("pivot:" + pivot);
			pt.setLocation(newX,newY);
		}

		public void mousePressed(MouseEvent e) {
			System.out.println(fromX(e.getX()) + " " + fromY(e.getY()));
			Point pt = new Point((int)fromX(e.getX()),(int)fromY(e.getY()));
			select = false;
			LinkedList<DoozerNode> nodeList = new LinkedList<DoozerNode>();
			nodeList.add(model.getRoot());
			a.add(new Point(pt));
			while(!nodeList.isEmpty()){
				DoozerNode node = nodeList.removeFirst();
				Point pivot = node.getPivot();
				rotatePoint(pt, pivot, node.getAngle());
				System.out.println(pt);
				int x = (int)fromX(pt.x);
				int y = (int)fromY(pt.y);
System.out.println("HIHI");
b.add(new Point(pt));
				
				for(DoozerNode dn : node.getChildren()){
					nodeList.addFirst(dn);
				}
			}
			repaint();
		}

		/**
		 * The mouse moved. Check if it's over the dragable regions and adjust
		 * the cursor accordingly.
		 */
		public void mouseMoved(MouseEvent e) {
			if (selected) {
					//setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
					//setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

		/** The user is dragging the mouse. Resize appropriately. */
		public void mouseDragged(MouseEvent e) {
			if (this.canDragX) {
				//model.setBase(fromX(e.getX()));
			}
			if (this.canDragY) {
				//model.setHeight(fromY(e.getY()));
			}
		} // mouseDragged
	} // MController
} // GraphicalView

