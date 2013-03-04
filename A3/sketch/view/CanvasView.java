package sketch.view;

import sketch.model.IView;
import sketch.model.SketchModel;
import sketch.Main;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.*;

class MController extends MouseInputAdapter{
	long prevTime = System.nanoTime();

	public void mousePressed(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
System.out.println("HIHI");
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

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(100,0,100,100);
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
System.out.println("HIHI");
		}
	}
}
