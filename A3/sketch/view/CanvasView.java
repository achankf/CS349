package sketch.view;

import sketch.model.*;
import sketch.model.object.*;
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

	protected Shape buffer = null;
	protected ArrayList<DrawableObject> selected = new ArrayList<DrawableObject>();

	public CanvasView(){
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
			public void resetView(){
				updateView();
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
			Draw.rectPoint(g2d, findCentreOfSelected(), 10);
		}
	}

	public Point findCentreOfSelected(){
		if (buffer == null) return null;
		Rectangle rect = buffer.getBounds();
		return new Point((int)(rect.getX() + rect.getWidth() / 2),
			(int)(rect.getY() + rect.getHeight() / 2));
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int currentFrame = Main.slider.getValue();
		for (DrawableObject obj : Main.model.getObjLst()){
			if (!obj.exist(currentFrame)) continue;
			obj.draw(g2d, currentFrame);
		}

		drawSelectedArea(g2d);

		g2d.setColor(Color.RED);
		for (DrawableObject obj : selected){
			obj.draw(g2d, currentFrame);
		}
		g2d.setColor(Color.BLACK);
	}

	public void changeModeGarbageCollect(){
		selected.clear();
		buffer = null;
	}

	public void setDrawMode(){
		changeModeGarbageCollect();
		registerControllers(drawMode);
		Main.model.resetAllViews();
	}

	public void setSelectMode(){
		changeModeGarbageCollect();
		registerControllers(selectMode);
		Main.model.resetAllViews();
	}

	public void setEraseMode(){
		changeModeGarbageCollect();
		registerControllers(eraseMode);
		Main.model.resetAllViews();
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
			int frame = Main.slider.getValue();
			obj = new DrawableObject(frame);
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
		Boolean alreadySelected = false;
		Point prevMouseLoc;
		Path path;
		long startTime;
		int numTicks;
		int startTick;

		public void mousePressed(MouseEvent e) {
			prevMouseLoc = e.getPoint();
			numTicks = 0;
			startTick = Main.slider.getValue();
			if (!selected.isEmpty() && buffer.contains(e.getX(), e.getY())){
				path = selected.get(0).getPath();
				if (path == null){
					path = new Path(findCentreOfSelected());
				}
				for (DrawableObject obj : selected){
					obj.setPath(path);
				}
				alreadySelected = true;
				startTime = System.nanoTime();
				return;
			}
			alreadySelected = false;
			buffer = new Polygon();
			selected.clear();
			repaint();
		}

		public void mouseDragged(MouseEvent e) {
			if(alreadySelected){
				//if (startTime - Config.TICK_PER_NANOSEC < 0) return;
				startTime = System.nanoTime();
				path.addDelta(startTick + numTicks, PointTools.ptDiff(e.getPoint(), prevMouseLoc));
				prevMouseLoc = e.getPoint();
				Main.slider.setValue(startTick + numTicks++);
				return;
			}
			Point pt = e.getPoint();
			((Polygon)buffer).addPoint((int)pt.x, (int)pt.y);
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			if (!alreadySelected){
				pushSelectedObj();
			}
			if (selected.isEmpty()) buffer = null;
			else setTightSelectBounds();
			repaint();
		}

		public void setTightSelectBounds(){
			Polygon temp = new Polygon();
			for (DrawableObject obj : selected){
				for (Point pt : obj.getPtLst()){
					temp.addPoint((int)pt.x, (int)pt.y);
				}
			}
			buffer = temp.getBounds();
		}

		public void pushSelectedObj(){
			for (DrawableObject obj : Main.model.getObjLst()){
				if (obj.containedIn(buffer)){
					selected.add(obj);
				}
			}
		}
	}

	class EraseMode extends MouseInputAdapter{
		public void mouseDragged(MouseEvent e) {
			Point pt = e.getPoint();
			Shape sp = new Rectangle((int)(e.getX() -2), (int)(e.getY()-2), 4, 4);
			int frame = Main.slider.getValue();
			for (Iterator<DrawableObject> it =  Main.model.getObjLst().iterator(); it.hasNext();){
				DrawableObject obj = it.next();
				if (!obj.exist(frame))continue;
				if (obj.containedPartlyIn(sp)){
					obj.erasedAt(frame);
					// object's beginning and end are equal -- can be removed
					if (obj.nonExistence()){
						it.remove();
					}
				}
			}
			repaint();
		}
	}
}
