package sketch.view;

import sketch.model.*;
import sketch.model.object.DrawableObject;
import sketch.*;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.*;

public final class CanvasView extends JComponent{
	public final MouseInputAdapter drawMode = new DrawMode();
	public final MouseInputAdapter eraseMode = new EraseMode();
	public final MouseInputAdapter selectMode = new SelectMode();

	public Polygon buffer = null;

	public CanvasView(){
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
		setDrawMode();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(100,0,100,100);

		for (DrawableObject obj : Main.model.getObjLst()){
			obj.draw(g2d,0);
		}

		if (buffer != null){
			g2d.drawPolygon(buffer);
		}
	}

	public void setDrawMode(){
		registerControllers(drawMode);
	}

	public void setSelectMode(){
		registerControllers(selectMode);
	}

	public void setEraseMode(){
		registerControllers(eraseMode);
	}

	private void registerControllers(MouseInputListener mil) {
		buffer = null;

		this.removeMouseListener(drawMode);
		this.removeMouseListener(eraseMode);
		this.removeMouseListener(selectMode);

		this.removeMouseMotionListener(drawMode);
		this.removeMouseMotionListener(eraseMode);
		this.removeMouseMotionListener(selectMode);

		this.addMouseListener(mil);
		this.addMouseMotionListener(mil);
	}

	class DrawMode extends MouseInputAdapter{
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
			if (obj == null) return;
			obj.finalize();
			obj = null;
			repaint();
 		}
	}

	class SelectMode extends MouseInputAdapter{

		public void mousePressed(MouseEvent e) {
			buffer = new Polygon();
		}

		public void mouseDragged(MouseEvent e) {
			Point pt = e.getPoint();
			buffer.addPoint((int)pt.x, (int)pt.y);
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	class EraseMode extends MouseInputAdapter{

		public void mousePressed(MouseEvent e) {
			buffer = new Polygon();
		}

		public void mouseDragged(MouseEvent e) {
			Point pt = e.getPoint();
			buffer.addPoint((int)pt.x, (int)pt.y);
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			repaint();
		}
	}
}
