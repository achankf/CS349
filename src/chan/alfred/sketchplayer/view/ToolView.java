package chan.alfred.sketchplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import chan.alfred.sketchplayer.model.IView;
import chan.alfred.sketchplayer.*;
import chan.alfred.sketchplayer.model.SketchModel;
import java.io.*;
/*
public class ToolView extends View{
	private final SketchModel model;

	public ToolView(Context context, AttributeSet attrs){
		super(context,attrs);
		this.model = MainActivity.model;

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
*/