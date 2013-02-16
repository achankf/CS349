package doozerSimulator.model;

import doozerSimulator.view.IView;
import java.util.ArrayList;
import java.awt.Point;

public class BaseModel extends Object{
	/* A list of the model's views. */
	private ArrayList<IView> views = new ArrayList<IView>();

	/** Add a new view of this triangle. */
	public void addView(IView view) {
		this.views.add(view);
		view.updateView();
	}

	/** Remove a view from this triangle. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this triangle. */
	protected void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
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

	public double calculateAngle(Point pt){
		double angle = Math.acos(pt.x / Math.sqrt(Math.pow(pt.x,2) + Math.pow(pt.y,2)));
		// negative due to the origin of canvas is location on the top-left
		return -(pt.y < 0 ? -angle : angle);
	}
};
