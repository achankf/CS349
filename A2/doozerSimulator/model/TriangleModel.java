package doozerSimulator.model;

// Note!  Nothing from the view package is imported here.
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;

public class TriangleModel extends BaseModel{
	// Limit the size of the triangle.
	public static final double MAX_SIDE = 100.0;
	public static final double MAX_HYPO = 100.0;//Math.sqrt(MAX_SIDE * MAX_SIDE + MAX_SIDE * MAX_SIDE);

	private double base = 50.0; // length of the base
	private double height = 50.0; // height of the triangle

	// Override the default construtor, making it private.
	public TriangleModel() {
	}


	/** Set the base to a new value. Must be between 0 and MAX_BASE. */
	public void setBase(double theBase) {
		double tmp = Math.max(0, theBase);
		this.base = Math.min(tmp, MAX_SIDE);
		this.updateAllViews();
	}

	/** Set the height to a new value. Must be between 0 and MAX_HEIGHT. */
	public void setHeight(double theHeight) {
		this.height = Math.min(Math.max(0, theHeight), MAX_SIDE);
		this.updateAllViews();
	}

	/** Set both the base and the height to new values. */
	public void setValues(double theBase, double theHeight) {
		this.base = Math.min(Math.max(0, theBase), MAX_SIDE);
		this.height = Math.min(Math.max(0, theHeight), MAX_SIDE);

		this.updateAllViews();
	}

	/** Get the length of this triangle's base. */
	public double getBase() {
		return this.base;
	}

	/** Get this triangle's height. */
	public double getHeight() {
		return this.height;
	}

	/** Get the length of this triangle's hypotenuse. */
	public double getHypotenuse() {
		return Math.sqrt(this.base * this.base + this.height * this.height);
	}
}
