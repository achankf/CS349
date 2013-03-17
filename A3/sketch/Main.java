package sketch;

import sketch.model.*;
import sketch.view.*;

import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public final class Main{

	public static void main(String[] args){
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		final SketchModel model = new SketchModel();
		final CanvasView canvas = new CanvasView(model);
		final ToolView tool = new ToolView(model, canvas.getModeState());
		final SliderView slider = new SliderView(model, executor);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(Config.TITLE);
				JPanel panel = new JPanel();
				JScrollPane jsp = new JScrollPane(canvas);

				panel.setLayout(new BorderLayout());
				panel.add(tool, BorderLayout.NORTH);
				panel.add(jsp, BorderLayout.CENTER);
				panel.add(slider, BorderLayout.SOUTH);

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(Config.DIM);
				frame.setContentPane(panel);
		    frame.setVisible(true);
			}
		});
	}
}
