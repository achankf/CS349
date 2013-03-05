package sketch.view;

import sketch.model.*;
import sketch.model.object.DrawableObject;
import sketch.*;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.util.*;

public final class CanvasView extends JComponent{
	public final MouseInputAdapter drawMode = new DrawMode();
	public final MouseInputAdapter eraseMode = new EraseMode();
	public final MouseInputAdapter selectMode = new SelectMode();

	protected Polygon buffer = null;
	protected ArrayList<DrawableObject> selected = new ArrayList<DrawableObject>();

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

	private void drawSelectedArea(Graphics2D g2d){
		if (buffer != null){
			g2d.setColor(Config.SELECTED_COLOUR);
			Stroke temp = g2d.getStroke();
			g2d.setStroke(Config.DASHED);
			g2d.draw(buffer);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(temp);
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(100,0,100,100);

		for (DrawableObject obj : Main.model.getObjLst()){
			obj.draw(g2d,0);
		}

		drawSelectedArea(g2d);

		g2d.setColor(Color.RED);
		for (DrawableObject obj : selected){
			obj.draw(g2d,0);
		}
		g2d.setColor(Color.BLACK);
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
		selected.clear();

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

	public void findSelected(){
		for (DrawableObject obj : Main.model.getObjLst()){
			if (obj.containedIn(buffer)){
				selected.add(obj);
			}
		}
	}

	class SelectMode extends MouseInputAdapter{
		Boolean alreadySelected = false;

		public void mousePressed(MouseEvent e) {
			if (!selected.isEmpty() && buffer.contains(e.getX(), e.getY())){
				alreadySelected = true;
				return;
			}
			alreadySelected = false;
			buffer = new Polygon();
			selected.clear();
			repaint();
		}

		public void mouseDragged(MouseEvent e) {
			if(alreadySelected){
				
				return;
			}
			Point pt = e.getPoint();
			buffer.addPoint((int)pt.x, (int)pt.y);
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			findSelected();
			if (selected.isEmpty()){
				buffer = null;
			}
			repaint();
		}
	}

	class EraseMode extends MouseInputAdapter{

		public void mousePressed(MouseEvent e) {
			buffer = new Polygon();
			selected.clear();
			repaint();
		}

		public void mouseDragged(MouseEvent e) {
			Point pt = e.getPoint();
			buffer.addPoint((int)pt.x, (int)pt.y);
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			findSelected();
			for (DrawableObject obj : selected){
					Main.model.removeObject(obj);
			}
			buffer = null;
			selected.clear();
			repaint();
		}
	}
}
