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
	private Boolean doNotSchedulePlayBack = false;
	private final SketchModel model;
	private ScheduledExecutorService executor;

	public SliderView(SketchModel sModel, ScheduledExecutorService sExecutor){
		this.model = sModel;
		this.executor = sExecutor;
		JButton play = new JButton("Play");
		JButton stop = new JButton("Stop");

		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startPlaying();
			}
		});

		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stopPlaying();
			}
		});

		this.setLayout(new BorderLayout());
		this.add(slider, BorderLayout.CENTER);

		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BorderLayout());

		westPanel.add(play, BorderLayout.WEST);
		westPanel.add(stop, BorderLayout.EAST);
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
		model.resetAllViews();
		Runnable runnable = new Runnable(){
			public void run(){
				javax.swing.SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						long prevTime = System.nanoTime();
						int frame = slider.getValue();
						if (frame >= slider.getMaximum()){
							//doNotSchedulePlayBack = true;
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
		if (future == null) return;
		future.cancel(true);
	}

	public void setValue(int val){
		slider.setValue(val);
	}

	public int getValue(){
		return slider.getValue();
	}

	private class myChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
			model.setFrame(((JSlider)(e.getSource())).getValue());
			model.updateAllViews();
		}
	}

	private class TimeSlider extends JSlider{
		TimeSlider(){
			super(JSlider.HORIZONTAL, 0, Config.SLIDER_MAX, 0);
			setMajorTickSpacing(Config.FPS);
			setPaintTicks(true);
			setPaintLabels(false);
			addChangeListener(new myChangeListener());
		}
	}
}
