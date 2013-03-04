package sketch.view;

import sketch.model.IView;
import sketch.Main;
import sketch.model.SketchModel;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

class MController extends MouseInputAdapter{
	public void mousePressed(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
	}
}

public class ToolView extends JComponent{

	public ToolView(){
		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
		registerControllers();
	}

	private void registerControllers() {
		MouseInputListener mil = new MController();
		this.addMouseListener(mil);
		this.addMouseMotionListener(mil);
	}
}
