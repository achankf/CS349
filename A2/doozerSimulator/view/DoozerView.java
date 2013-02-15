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
		
		Point prevPivot = model.getStartPoint();
		g2d.fillOval(toX(prevPivot.x)-5,toY(prevPivot.y)-5,10,10);

		DoozerNode node = model.getRoot();
		while(true){
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
			if (!node.hasNext()) break;
			node = node.getNext();
		}

		prevPivot = model.getStartPoint();
		node = model.getRoot();
		while(true){
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
			g2d.setColor(Color.black);
			g2d.drawRect(x,y,node.getWidth(),node.getHeight());
			prevPivot = pivot;
			if (!node.hasNext()) break;
			node = node.getNext();
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
		private boolean selected = false;
		private DoozerNode selectedNode = null;

		public Boolean contains(DoozerNode node, Point pt){
			return false;
		}

		public void rotatePoint(Point pt, Point pivot, double angle){
			double cost = Math.cos(angle);
			double sint = Math.sin(angle);
			double tempX = pt.x - pivot.x;
			double tempY = pt.y - pivot.y;
			double newX = tempX * cost - tempY * sint + pivot.x;
			double newY = tempX * sint + tempY * cost + pivot.y;
			pt.setLocation(newX,newY);
		}

		public void mousePressed(MouseEvent e) {
a.clear();
b.clear();
			selected = false;
			Point pt = new Point((int)fromX(e.getX()),(int)fromY(e.getY()));
			a.add(new Point(pt));
			int i =0;
			DoozerNode node = model.getRoot();
			while(true){
				Point pivot = node.getPivot();
				rotatePoint(pt, pivot, node.getAngle());
				if (node.contains(pt) && !selected){
					selected = true;
					selectedNode = node;
					System.out.println("HIT " + i);
				}
b.add(new Point(pt));
				i++;
				if (!node.hasNext()) break;
				node = node.getNext();
			}
			repaint();
		}

		/**
		 * The mouse moved. Check if it's over the dragable regions and adjust
		 * the cursor accordingly.
		 */
		public void mouseMoved(MouseEvent e) {
			if (selected) {
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
					//setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

		/** The user is dragging the mouse. Resize appropriately. */
		public void mouseDragged(MouseEvent e) {
			if (!selected || selectedNode == null) return;
			Point pt = e.getPoint();
			Point pivot = selectedNode.getPivot();
//System.out.println(e.getPoint());
			double vx = pt.x;
			double vy = pt.y;
a.clear();
a.add(new Point((int)vx,(int)vy));
			double angle = Math.acos(vx / Math.sqrt(Math.pow(vx,2) + Math.pow(vy,2)));
//System.out.println(vx);
			if (vy < 0){
				angle = -angle;
			}
/*
System.out.println(angle);
			if (angle > selectedNode.getAngle() && selectedNode.getAngle() > -Math.PI / 2){
System.out.println("1:"+selectedNode.getAngle() + " " + Math.PI / 2);
				selectedNode.setAngle(selectedNode.getAngle() + 0.01);
			} else if (angle < selectedNode.getAngle() && selectedNode.getAngle() > -Math.PI / 2){
System.out.println("2:"+selectedNode.getAngle() + " " + Math.PI / 2);
				selectedNode.setAngle(selectedNode.getAngle() - 0.01);
			}
*/
/*
			double ninetyDegree = Math.PI / 2;
			if (angle > ninetyDegree){
				angle = ninetyDegree;
			} else if (angle < -ninetyDegree){
				angle = -ninetyDegree;
			}
			if (angle * selectedNode.getAngle() < 0){
				if(angle < 0){
					selectedNode.setAngle(selectedNode.getAngle() - 0.01);
				} else {
					selectedNode.setAngle(selectedNode.getAngle() + 0.01);
				}
			}else {
				selectedNode.setAngle(angle);
			}
*/
			selectedNode.setAngle(angle);
			System.out.println(angle);
			repaint();
		} // mouseDragged
	} // MController
} // GraphicalView

