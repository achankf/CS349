package sketch.view;

import sketch.model.IView;
import sketch.*;
import sketch.model.SketchModel;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.MouseEvent;
import java.awt.*;

public class ToolView extends JPanel{

	public ToolView(){
		JButton draw = new JButton("Draw");
		this.add(draw);

		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawRect(0,0,100,50);
	}
}
