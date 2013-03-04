package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import javax.swing.JComponent;
import java.awt.Color;

public class CanvasView extends JComponent{
	SketchModel model;

	public CanvasView(SketchModel model){
		super();
		this.model = model;
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		this.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
	}
}
