package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import sketch.Main;
import javax.swing.JSlider;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

class MController extends MouseInputAdapter{
	public void mousePressed(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
	}
}

public class SliderView extends JSlider{

	public SliderView(){
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
