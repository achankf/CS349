package sketch.view;

import sketch.model.*;
import sketch.model.object.DrawableObject;
import sketch.*;
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

		for (DrawableObject obj : Main.model.getObjLst()){
			obj.draw(g2d,0);
		}
	}

	private void registerControllers() {
		MouseInputListener mil = new MController();
		this.addMouseListener(mil);
		this.addMouseMotionListener(mil);
	}

	class MController extends MouseInputAdapter{
		DrawableObject obj = null;
		long prevTime = System.nanoTime();
	
		public void mousePressed(MouseEvent e) {
			obj = new DrawableObject();
			Main.model.addObject(obj);
		}

		public void mouseDragged(MouseEvent e) {
			obj.addPoint(e.getPoint());
			if (System.nanoTime() - prevTime < Config.TICK_PER_NANOSEC) return;
			prevTime = System.nanoTime();
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			obj.finalize();
			obj = null;
			if (System.nanoTime() - prevTime < Config.TICK_PER_NANOSEC) return;
			prevTime = System.nanoTime();
			repaint();
 		}
	}
}
