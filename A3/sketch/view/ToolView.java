package sketch.view;

import sketch.model.IView;
import sketch.Main;
import sketch.model.SketchModel;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public class ToolView extends JComponent{

	public ToolView(){
		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
		registerControllers();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0,0,100,100);
	}

	private void registerControllers() {
		MouseInputListener mil = new MController();
		this.addMouseListener(mil);
		this.addMouseMotionListener(mil);
	}

	class MController extends MouseInputAdapter{
		long prevTime = System.nanoTime();
	
		public void mousePressed(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
		}
	}
}
