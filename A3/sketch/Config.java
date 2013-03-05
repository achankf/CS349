package sketch;

import java.awt.*;

public class Config{
	public static int TIMELAST;
	public static Dimension DIM = new Dimension(800,600);
	public static Dimension CANVAS_SIZE = new Dimension(800,450);
	public static Dimension SLIDER_SIZE = new Dimension(800,50);
	public static Dimension TOOL_SIZE = new Dimension(800,100);
	public static String TITLE = "Alfred Chan 255";

	public static final int DRAWABLE_MIN = 200;

	public static final int FPS = 30;
	public static final long TICK_PER_NANOSEC = 1000000000 / FPS;

	public static final Color SELECTED_COLOUR = Color.BLUE;

	private final static float DASH1[] = {10.0f};
	public final static BasicStroke DASHED = new BasicStroke(1.0f,
		BasicStroke.CAP_BUTT,
		BasicStroke.JOIN_MITER,
		10.0f, DASH1, 0.0f);
}
