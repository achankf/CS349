package chan.alfred.sketchplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import chan.alfred.sketchplayer.model.*;
import chan.alfred.sketchplayer.model.object.*;
import chan.alfred.sketchplayer.*;

public final class CanvasView extends View{
	private final SketchModel model = MainActivity.model;

	public CanvasView(Context context) {
		super(context);
		addView();
	}
	
	public CanvasView(Context context, AttributeSet attrs){
		super(context, attrs);
		addView();
	}
	
	public CanvasView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		addView();
	}
	
	private void addView(){
		model.addView(new IView(){
			@Override
			public void resetView(){
				updateView();
			}
			@Override
			public void updateView(){
				invalidate();
			}
		});
	}

	@Override
	public void draw(Canvas g2d) {
		int currentFrame = model.getFrame();
		for (DrawableObject obj : model.getObjLst()){
			if (!obj.exist(currentFrame)) continue;
			obj.draw(g2d, currentFrame);
		}
	}
}
