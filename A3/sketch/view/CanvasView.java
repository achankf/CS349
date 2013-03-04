package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import sketch.Main;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.Color;

class MController extends MouseInputAdapter{
	public void mousePressed(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
	}
}

public final class CanvasView extends JComponent{

	public CanvasView(){
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
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
