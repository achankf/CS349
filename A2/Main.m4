package M4_PACKAGE_NAME;

import javax.swing.*;
import java.awt.*;

public class Main{
	public static void createAndShowGUI(){
		JFrame frame = new JFrame("M4_TITLE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
