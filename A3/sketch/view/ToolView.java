package sketch.view;

import sketch.model.IView;
import sketch.*;
import sketch.model.SketchModel;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.filechooser.*;

public class ToolView extends JPanel{
	private final SketchModel model;
	private final CanvasView.ModeState ms;

	public ToolView(SketchModel sModel, CanvasView.ModeState modeState){
		this.model = sModel;
		this.ms = modeState;
		final JButton draw = new JButton("Draw");
		final JButton erase = new JButton("Erase");
		final JButton select = new JButton("Select");
		final JButton save = new JButton("Save");
		final JButton load = new JButton("Load");
		draw.setEnabled(false);

		draw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ms.setMode(ms.getDraw());
				draw.setEnabled(false);
				select.setEnabled(true);
				erase.setEnabled(true);
			}
		});

		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ms.setMode(ms.getSelect());
				draw.setEnabled(true);
				select.setEnabled(false);
				erase.setEnabled(true);
			}
		});

		erase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ms.setMode(ms.getErase());
				draw.setEnabled(true);
				select.setEnabled(true);
				erase.setEnabled(false);
			}
		});

		final JPanel temp = this;
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Sketch files only", "sketch");
		fc.setFileFilter(filter);

		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.resetAllViews();
				try{
					int returnVal = fc.showOpenDialog(temp);
					if (returnVal == JFileChooser.APPROVE_OPTION){
						File f = fc.getSelectedFile();
						String path = f.getPath();
						if(!path.toLowerCase().endsWith(".sketch")){
				    	f = new File(path + ".sketch");
						}
						save(f);
					}
				} catch (Exception exp){
					JOptionPane.showMessageDialog(temp, "Error: happened during save");
				}
			}
		});

		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.resetAllViews();
				try{
					int returnVal = fc.showOpenDialog(temp);
					if (returnVal == JFileChooser.APPROVE_OPTION){
						load(fc.getSelectedFile());
					}
				} catch (Exception exp){
					JOptionPane.showMessageDialog(temp, "Please choose the correct file");
				}
				model.resetAllViews();
			}
		});

		this.setLayout(new FlowLayout());
		this.add(draw);
		this.add(erase);
		this.add(select);
		this.add(save);
		this.add(load);

		model.addView(new IView(){
			public void updateView(){
				repaint();
			}
			public void resetView(){
				updateView();
			}
		});
	}

	public void save(File fileLocation) throws IOException{
		OutputStream ostream = new FileOutputStream(fileLocation);
		DataOutputStream out = new DataOutputStream(ostream);
		model.write(out);
		out.close();
	}

	public void load(File fileLocation) throws IOException{
		InputStream istream = new FileInputStream(fileLocation);
		DataInputStream in = new DataInputStream(istream);
		model.read(in);
		in.close();
	}
}
