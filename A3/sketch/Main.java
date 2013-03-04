package sketch;

import sketch.model.*;
import sketch.view.*;

import javax.swing.*;
import java.awt.BorderLayout;

class SketchPanel extends JPanel{
	public SketchPanel(CanvasView canvas, ToolView tool, SliderView slider){
		super();
		this.setLayout(new BorderLayout());
		this.add(tool, BorderLayout.NORTH);
		this.add(canvas, BorderLayout.CENTER);
		this.add(slider, BorderLayout.SOUTH);
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
	private static SketchModel model = new SketchModel();
	private static CanvasView canvas = new CanvasView(model);
	private static ToolView tool = new ToolView(model);
	private static SliderView slider = new SliderView(model);

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JPanel panel = new SketchPanel(canvas, tool, slider);
				JFrame frame = new SketchFrame(panel);
			}
		});
	}
}
