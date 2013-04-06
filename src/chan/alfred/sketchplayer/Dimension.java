package chan.alfred.sketchplayer;

import android.util.Pair;

public class Dimension extends Pair<Integer, Integer> {

	public Dimension(Integer first, Integer second) {
		super(first, second);
	}
	
	public int getWidth(){
		return first;
	}
	
	public int getHeight(){
		return second;
	}
}
