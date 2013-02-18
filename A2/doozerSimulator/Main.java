package doozerSimulator;

import javax.swing.*;
import doozerSimulator.model.MachineModel;
import doozerSimulator.view.MachineView;
import java.awt.event.*;

final public class Main{
	public static MachineModel model = new MachineModel(System.nanoTime());
	public static MachineView canvas = new MachineView(model);

	private class GameOver extends Exception{
	}

	public static void createAndShowGUI(){
		JFrame frame = new JFrame("Alfred Chan 255");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Timer t = new Timer(1000/30, new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if (!model.gameOver()){
							model.update(System.nanoTime());
						}
						canvas.repaint();
					}
				});
				t.start(); }
		});

		try{
			Thread.sleep(10);
		} catch(Exception e){
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Timer t = new Timer(Config.SPAWN_TIME, new ActionListener(){
					public void actionPerformed(ActionEvent e){
						model.checkGameOver();
						if (model.gameOver()) return;
						model.spawnCandy(Config.SPAWN_NUMBER);
					}
				});
				t.start();
			}
		});

	}
}
