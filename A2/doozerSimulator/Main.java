package doozerSimulator;

import javax.swing.*;
import doozerSimulator.model.DoozerModel;
import doozerSimulator.view.DoozerView;

final public class Main{
	public static void createAndShowGUI(){
		JFrame frame = new JFrame("Alfred Chan 255");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DoozerModel model = new DoozerModel();
		JComponent canvas = new DoozerView(model);

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
	}
}
