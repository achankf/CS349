package sketch.view;

import sketch.model.*;
import sketch.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.concurrent.*;

public class SliderView extends JPanel{
	private JSlider slider = new TimeSlider();
	private ScheduledFuture<?> future;
	private final SketchModel model;
	private ScheduledExecutorService executor;

	private JButton play = new JButton("Play");
	private boolean playing = false;
	private boolean controlPressed = false;
	

	public SliderView(SketchModel sModel, ScheduledExecutorService sExecutor){
		this.model = sModel;
		this.executor = sExecutor;

		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Boolean temp = playing;
				model.resetAllViews();
				if (!temp){
					startPlaying();
				} else {
					stopPlaying();
				}
			}
		});

		final JButton addFrames = new JButton("Add " + Config.FRAMES_PER_ADD + " frames");

		addFrames.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int maxFrame = model.getMaxFrame() + Config.FRAMES_PER_ADD;
				model.setMaxFrame(maxFrame);
				slider.setMaximum(maxFrame);
				model.resetAllViews();
			}
		});

		this.setLayout(new BorderLayout());
		this.add(slider, BorderLayout.CENTER);
		this.add(addFrames, BorderLayout.EAST);

		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());

		westPanel.add(play, BorderLayout.WEST);
		this.add(westPanel, BorderLayout.WEST);

		model.addView(new IView(){
			public void updateView(){
				setValue(model.getFrame());
				repaint();
			}

			public void resetView(){
				stopPlaying();
				updateView();
			}
		});
	}

	public void startPlaying(){
		play.setText("Stop");
		playing = true;

		model.updateAllViews();
		Runnable runnable = new Runnable(){
			public void run(){
				javax.swing.SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						long prevTime = System.nanoTime();
						int frame = slider.getValue();
						if (frame >= slider.getMaximum()){
							stopPlaying();
							return;
						}
						slider.setValue(frame + 1);
						model.updateAllViews();
						prevTime = System.nanoTime();
					}
				});
			}
		};
		future = executor.scheduleAtFixedRate(runnable,0, Config.TICK_PER_NANOSEC, TimeUnit.NANOSECONDS);
	}

	public void stopPlaying(){
		play.setText("Play");
		playing = false;
		if (future == null) return;
		future.cancel(true);
	}

	public void setValue(int val){
		slider.setValue(val);
	}

	public int getValue(){
		return slider.getValue();
	}

	private class TimeSlider extends JSlider{
		TimeSlider(){
			super(JSlider.HORIZONTAL, 0, Config.SLIDER_MAX, 0);
			setMajorTickSpacing(Config.FPS);
			setPaintTicks(false);
			setPaintLabels(false);
			addChangeListener(new myChangeListener());
		}

		private class myChangeListener implements ChangeListener {
			long time = System.nanoTime();
			public void stateChanged(ChangeEvent e){
				model.setFrame(((JSlider)(e.getSource())).getValue());
				model.updateAllViews();
			}
		}
	}
}
