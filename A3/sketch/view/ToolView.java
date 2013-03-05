package sketch.view;

import sketch.model.IView;
import sketch.*;
import sketch.model.SketchModel;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class ToolView extends JPanel{

	public ToolView(){
		JButton draw = new JButton("Draw");
		JButton erase = new JButton("Erase");
		JButton select = new JButton("Select");

		draw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.canvas.setDrawMode();
			}
		});

		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.canvas.setSelectMode();
			}
		});

		erase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.canvas.setEraseMode();
			}
		});

		this.setLayout(new FlowLayout());
		this.add(draw);
		this.add(erase);
		this.add(select);

		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
	}
}
