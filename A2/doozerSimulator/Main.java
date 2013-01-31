package doozerSimulator;

import javax.swing.*;
import doozerSimulator.*;

final public class Main{
	public static void createAndShowGUI(){
		JFrame frame = new JFrame("Alfred Chan 255");
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
