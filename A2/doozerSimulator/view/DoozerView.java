package doozerSimulator.view;

import doozerSimulator.model.*;
import doozerSimulator.objects.*;
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

	private double scale = 0.5; // how much should the triangle be scaled?
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
	  for(Point pt : a){
      g2d.fillOval(toX(pt.x)-5,toY(pt.y)-5,10,10);
    }
    for(Point pt : b){
      g2d.setColor(Color.red);
      g2d.fillOval(toX(pt.x)-5,toY(pt.y)-5,10,10);
    }	

		Doozer doozer = model.getDoozer();
		for (int i = 0; i < doozer.getNumArms(); i++){
			int x = toX((int)doozer.getArmX(i));
			int y = toY((int)doozer.getArmY(i));
			int pivotX = toX(doozer.getPivotX(i));
			int pivotY = toY(doozer.getPivotY(i));
			int width = (int)(doozer.getArmWidth() * scale);
			int armHeight = (int)(doozer.getArmHeight() * scale);
			g2d.setColor(Color.black);
			g2d.fillOval(pivotX-5,pivotY-5,10,10);
			g2d.setColor(Color.black);
			g2d.drawRect(x,y,width,armHeight);
//System.out.println(x + " " +y + " " + pivotX + " " +pivotY);
		}
		for (int i = 0; i < doozer.getNumArms(); i++){
			int x = toX((int)doozer.getArmX(i));
			int y = toY((int)doozer.getArmY(i));
			int pivotX = toX(doozer.getPivotX(i));
			int pivotY = toY(doozer.getPivotY(i));
			int width = (int)(doozer.getArmWidth() * scale);
			int armHeight = (int)(doozer.getArmHeight() * scale);
			g2d.setColor(Color.black);
			g2d.fillOval(pivotX-5,pivotY-5,10,10);
			g2d.rotate(doozer.getAngle(i),pivotX,pivotY); 
			g2d.setColor(Color.black);
			g2d.drawRect(x,y,width,armHeight);
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

	protected Point from(Point pt){
		return new Point((int)fromX(pt.x),(int)fromY(pt.y));
	}

	protected Point to(Point pt){
		return new Point((int)toX(pt.x),(int)toY(pt.y));
	}

	private class MController extends MouseInputAdapter {
		private int selected = -1;

		public void mousePressed(MouseEvent e) {
			a.clear();
			b.clear();
			selected = -1;
			Doozer doozer= model.getDoozer();
			Point pt = new Point((int)fromX(e.getX()),(int)fromY(e.getY()));
			a.add(new Point(pt));
			for (int i = 0; i < doozer.getNumArms(); i++){
				Point pivot = new Point(doozer.getPivot(i));
				model.rotatePoint(pt,pivot, doozer.getAngle(i));
				if (doozer.contains(i,pt) && selected == -1){
					selected = i;
				}
				b.add(new Point(pt));
			}
			repaint();
		}

		/**
		 * The mouse moved. Check if it's over the dragable regions and adjust
		 * the cursor accordingly.
		 */
		public void mouseMoved(MouseEvent e) {
			if (selected != -1) {
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
					//setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

		/** The user is dragging the mouse. Resize appropriately. */
		public void mouseDragged(MouseEvent e) {
			if (selected == -1) return;
			Doozer doozer = model.getDoozer();
			Point pt = new Point(from(e.getPoint()));
			for (int i = 0; i < selected; i++){
				model.rotatePoint(pt, doozer.getPivot(i),doozer.getAngle(i));
			}
			double angle = model.calculateAngle(doozer.componentPos(selected,pt));
			doozer.setAngle(selected,angle);
			repaint();
		} // mouseDragged
	} // MController
} // GraphicalView

