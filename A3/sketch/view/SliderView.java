package sketch.view;

import sketch.model.*;
import sketch.*;
import javax.swing.JSlider;
import java.awt.event.*;
import javax.swing.event.*;

public class SliderView extends JSlider{

	public SliderView(){
		super(JSlider.HORIZONTAL, 0, Config.SLIDER_MAX, 0);
		setMajorTickSpacing(Config.FPS);
		setPaintTicks(true);
		setPaintLabels(false);

		addChangeListener(new myChangeListener());

		Main.model.addView(new IView(){
			public void updateView(){
				repaint();
			}
		});
	}

	private class myChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
			Main.model.updateAllViews();
		}
	}
}
