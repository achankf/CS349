package chan.alfred.sketchplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import chan.alfred.sketchplayer.model.*;
import chan.alfred.sketchplayer.model.object.*;
import chan.alfred.sketchplayer.*;

public final class CanvasView extends View{
	private final SketchModel model;
	
	private void addView(){
		model.addView(new IView(){
			public void updateView(){
				invalidate();
			}
			public void resetView(){
				updateView();
			}
		});
		model.updateAllViews();
	}

	public CanvasView(Context context) {
		super(context);
		this.model = MainActivity.model;
		addView();
	}
	
	public CanvasView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.model = MainActivity.model;
		addView();
	}
	
	public CanvasView(Context context, AttributeSet attrs){
		super(context, attrs);
		this.model = MainActivity.model;
		addView();
	}

	public void draw(Canvas g2d) {
		int currentFrame = model.getFrame();
		for (DrawableObject obj : model.getObjLst()){
			if (!obj.exist(currentFrame)) continue;
			obj.draw(g2d, currentFrame);
		}
	}
}
