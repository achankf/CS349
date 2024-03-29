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
};
