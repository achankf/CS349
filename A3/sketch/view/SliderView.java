package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import javax.swing.JSlider;

public class SliderView extends JSlider implements IView{
	SketchModel model;

	public SliderView(SketchModel model){
		super();
		this.model = model;
	}

	public void updateView(){
	}
}
