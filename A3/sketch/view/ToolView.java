package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import javax.swing.JComponent;

public class ToolView extends JComponent implements IView{
	SketchModel model;

	public ToolView(SketchModel model){
		super();
		this.model = model;
	}

	public void updateView(){
	}
}
