package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import javax.swing.JSlider;

public class SliderView extends JSlider{
	SketchModel model;

	public SliderView(SketchModel model){
		super();
		this.model = model;
		this.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
	}
}
