package sketch;

import sketch.model.*;
import sketch.view.*;

import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;

public final class Main{
	private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private static final SketchModel model = new SketchModel();
	private static final CanvasView canvas = new CanvasView(model);
	private static final ToolView tool = new ToolView(model, canvas.getModeState());
	private static final SliderView slider = new SliderView(model, executor);

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new SketchFrame();
			}
		});
	}

	private static class SketchPanel extends JPanel{
		public SketchPanel(){
			this.setLayout(new BorderLayout());

			JScrollPane jsp = new JScrollPane(Main.canvas);
			jsp.setMinimumSize(new Dimension(500,400));

			this.add(Main.tool, BorderLayout.NORTH);
			this.add(jsp, BorderLayout.CENTER);
			this.add(Main.slider, BorderLayout.SOUTH);
		}
	}
	
	private static class SketchFrame extends JFrame{
		public SketchFrame(){
			JPanel panel = new SketchPanel();
			JFrame frame = new JFrame(Config.TITLE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(Config.DIM);
			frame.setContentPane(panel);
	    frame.setVisible(true);
		}
	}
}
