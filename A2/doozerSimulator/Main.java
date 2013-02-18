package doozerSimulator;

import javax.swing.*;
import doozerSimulator.model.DoozerModel;
import doozerSimulator.view.DoozerView;
import java.awt.event.*;

final public class Main{
	public static DoozerModel model = new DoozerModel();
	public static DoozerView canvas = new DoozerView(model);

	public static void createAndShowGUI(){
		JFrame frame = new JFrame("Alfred Chan 255");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*
		DoozerModel model = new DoozerModel();
		JComponent canvas = new DoozerView(model);
*/

		frame.setSize(Config.DEFAULT_DIM);
		frame.setContentPane(canvas);
    frame.setVisible(true);
	}
	public static void main(String []args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.update(canvas);
				canvas.repaint();
			}
		};
		Timer t = new Timer(100/30,listener);
		t.start();
	}
}
