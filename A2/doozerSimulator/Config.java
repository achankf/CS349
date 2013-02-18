package doozerSimulator;

import java.awt.Dimension;

public class Config{
	public final static String TITLE = "Alfred Chan 255";
	public final static Dimension DEFAULT_DIM = new Dimension (800,600);
	public final static Dimension DEFAULT_CANDY_MIN_DIM = new Dimension(50,50);
	public final static int CANDY_RANDOM_BOUND = 50;

	// spawning
	public final static int NUM_CANDIES = 5;
	public final static int SPAWN_NUMBER= 2;
	//public final static int SPAWN_TIME = 10000;
	public final static int SPAWN_TIME = 1000;

	public final static Dimension DOOZER_BODY_DIM = new Dimension (150,100);
	public final static Dimension DOOZER_ARM_DIM = new Dimension (200,25);

	public final static int SELECTED_NULL = -1;
	public final static int SELECTED_BODY = -2;

	public final static int LAND_HEIGHT = 0;
	public final static int FALL_SPEED = 10;

};
