package sketch.model;

import java.util.ArrayList;

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
	public void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
	}

	/** Update all the views that are viewing this triangle. */
	public void resetAllViews() {
		for (IView view : this.views) {
			view.resetView();
		}
	}
};
