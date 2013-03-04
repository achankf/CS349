package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import javax.swing.JComponent;

public class ToolView extends JComponent{
	SketchModel model;

	public ToolView(SketchModel model){
		super();
		this.model = model;
		this.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
	}
}
