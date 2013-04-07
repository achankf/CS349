package chan.alfred.sketchplayer;

import android.graphics.Color;
import android.graphics.Paint;

public class Config{
	public static Dimension DIM = new Dimension(800,600);
	public static String TITLE = "Alfred Chan 255";

	public static final int FPS = 30;
	public static final int COLLECTOR_MIN = 100;
	public static final long TICK_PER_NANOSEC = 1000000000 / FPS;
	public static final int FRAMES_PER_ADD = 5 * FPS;

	public static final int SELECTED_COLOUR = Color.BLUE;

	// slider
	public final static int SLIDER_MAX = 60 * FPS; // 
	
	public final static Paint DEFAULT_PAINT = new Paint();
}
