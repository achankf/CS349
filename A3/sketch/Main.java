package sketch;

import sketch.model.SketchModel;
import sketch.view.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;

class SketchPanel extends JPanel{
	public SketchPanel(){
		this.setLayout(new BorderLayout());
		this.add(Main.tool, BorderLayout.NORTH);
		this.add(Main.canvas, BorderLayout.CENTER);
		this.add(Main.slider, BorderLayout.SOUTH);
	}
}

class SketchFrame extends JFrame{
	public SketchFrame(JPanel panel){
		JFrame frame = new JFrame(Config.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Config.DIM);
		frame.setContentPane(panel);
    frame.setVisible(true);
	}
}

public final class Main{
	public static final SketchModel model = new SketchModel();
	public static final CanvasView canvas = new CanvasView();
	public static final ToolView tool = new ToolView();
	public static final SliderView slider = new SliderView();

	public static void main(String[] args){

		canvas.setPreferredSize(Config.CANVAS_SIZE);
		slider.setPreferredSize(Config.SLIDER_SIZE);
		tool.setPreferredSize(Config.TOOL_SIZE);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JPanel panel = new SketchPanel();
				JFrame frame = new SketchFrame(panel);
			}
		});
	}
}
