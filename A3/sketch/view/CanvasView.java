package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import javax.swing.JComponent;
import java.awt.Color;

public class CanvasView extends JComponent implements IView{
	SketchModel model;

	public CanvasView(SketchModel model){
		super();
		this.model = model;
		this.setBackground(Color.WHITE);
	}

	public void updateView(){
	}
}
