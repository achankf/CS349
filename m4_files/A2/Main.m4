package M4_PACKAGE_NAME;

import javax.swing.*;
import M4_PACKAGE_NAME.*;

final public class Main{
	public static void createAndShowGUI(){
		JFrame frame = new JFrame("M4_TITLE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameCanvas canvas = new GameCanvas();

		frame.setContentPane(canvas);
		frame.pack();
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
