package doozerSimulator.view;

import doozerSimulator.Config;
import doozerSimulator.model.*;
import doozerSimulator.objects.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

/**
 * A view of a right triangle that displays the triangle graphically and allows
 * the user to change the size by dragging the image with a mouse.
 */
public class DoozerView extends JComponent {
	private DoozerModel model;
	private LinkedList<Point> a,b;
	private double scale = 1.0;

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
		Insets insets = this.getInsets();
		this.scale = Math.min((this.getWidth() - insets.left - insets.right) / 1024.0,
			(this.getHeight() - insets.top - insets.bottom) / 768.0);
		scale = scale == 0 ? 1 : scale;

		Graphics2D g2d = (Graphics2D) g;
		
		model.drawAll(g2d, new Convert(scale){
			public Point fromCanvas(Point pt){
				return from(pt);
			}
			public Point toCanvas(Point pt){
				return to(pt);
			}
		});
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
		private SelectedPair selected = null;
		private Boolean magnetOn = false;

		public void mousePressed(MouseEvent e) {
			Point pt = new Point(from(e.getPoint()));
			selected = model.containsAll(pt);
			if (selected != null && selected.i == -1){
				magnetOn = !magnetOn;
			}
		}

		/** The user is dragging the mouse. Resize appropriately. */
		public void mouseDragged(MouseEvent e) {
			if (selected == null) return;
			if (selected.i == -1) return;
			Point pt = new Point(from(e.getPoint()));
			selected.comp.move(selected.i, pt);
			if (magnetOn){
System.out.println("Magnet ON");
			}
			repaint();
		} // mouseDragged
	} // MController
} // GraphicalView

