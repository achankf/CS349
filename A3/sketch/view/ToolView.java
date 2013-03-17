package sketch.view;

import sketch.model.IView;
import sketch.*;
import sketch.model.SketchModel;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class ToolView extends JPanel{
	private final SketchModel model;
	private final CanvasView.ModeState ms;

	public ToolView(SketchModel model, CanvasView.ModeState modeState){
		this.model = model;
		this.ms = modeState;
		JButton draw = new JButton("Draw");
		JButton erase = new JButton("Erase");
		JButton select = new JButton("Select");

		draw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ms.setMode(ms.getDraw());
			}
		});

		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ms.setMode(ms.getSelect());
			}
		});

		erase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ms.setMode(ms.getErase());
			}
		});

		this.setLayout(new FlowLayout());
		this.add(draw);
		this.add(erase);
		this.add(select);

		model.addView(new IView(){
			public void updateView(){
				repaint();
			}
			public void resetView(){
				updateView();
			}
		});
	}
}
