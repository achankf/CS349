package sketch;

import java.awt.Dimension;

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
}
