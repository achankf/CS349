package doozerSimulator.view;

import doozerSimulator.model.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

/**
 * A view of a right triangle that displays the triangle graphically and allows
 * the user to change the size by dragging the image with a mouse.
 */
public class DoozerView extends JComponent {
	private DoozerModel model;

	private double scale = 1.0; // how much should the triangle be scaled?

	private boolean selected = false; // did the user select the triangle to
	// resize it?

	// To format numbers consistently in the text fields.
	private static final NumberFormat formatter = NumberFormat
			.getNumberInstance();

	public DoozerView(DoozerModel model) {
		super();
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

	/*
		this.scale = Math.min((this.getWidth() - insets.left - insets.right)
				/ (TriangleModel.MAX_SIDE + 2),
				(this.getHeight() - insets.top - insets.bottom)
						/ (TriangleModel.MAX_SIDE + 2));
	*/

		double base = 0;//this.model.getBase();
		double height = 0;//this.model.getHeight();
		double hypo = 0; //this.model.getHypotenuse();

		int left = this.toX(0);
		int right = this.toX(base);
		int bottom = this.toY(0);
		int top = this.toY(height);

		// Draw the triangle
		g.setColor(Color.black);
		g.drawLine(left, bottom, right, bottom);
		g.drawLine(right, bottom, right, top);
		g.drawLine(left, bottom, right, top);

		// Label the edges
		g.drawString(formatter.format(base), left + (right - left) / 2, bottom);
		g.drawString(formatter.format(height), right, top + (bottom - top) / 2);
		g.drawString(formatter.format(hypo), left + (right - left) / 2 - 15,
				top + (bottom - top) / 2 - 15);

		if (this.selected) {
			/** Draw "handles" for resizing the triangle. */
			g.fillRect(right - 3, bottom - 3, 6, 6);
			g.fillRect(right - 3, top - 3, 6, 6);
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

	/**
	 * Does the given point (screen coordinates) lie on the right side of the
	 * triangle?
	 */
	private boolean onRightSide(int x, int y) {
		return false;
		//return Math.abs(fromX(x) - this.model.getBase()) < 4 && fromY(y) >= 0
		//		&& fromY(y) <= this.model.getHeight();
	}

	/**
	 * Does the given point (screen coordinates) lie on the upper right corner
	 * of the triangle?
	 */
	private boolean onTopCorner(int x, int y) {
		return false;
		//return Math.abs(fromX(x) - this.model.getBase()) < 4
		//		&& Math.abs(fromY(y) - this.model.getHeight()) < 4;
	}

	private class MController extends MouseInputAdapter {
		/*
		 * Select or deselect the triangle.
		 */
		private boolean canDragX = false;
		private boolean canDragY = false;

		public void mousePressed(MouseEvent e) {
			selected = onRightSide(e.getX(), e.getY());
			repaint();
		}

		/**
		 * The mouse moved. Check if it's over the dragable regions and adjust
		 * the cursor accordingly.
		 */
		public void mouseMoved(MouseEvent e) {
			if (selected) {
				if (onTopCorner(e.getX(), e.getY())) {
					this.canDragX = true;
					this.canDragY = true;
					setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				} else if (onRightSide(e.getX(), e.getY())) {
					this.canDragX = true;
					this.canDragY = false;
					setCursor(Cursor
							.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				} else {
					this.canDragX = false;
					this.canDragY = false;
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
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

