package chan.alfred.sketchplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.SeekBar;
import chan.alfred.sketchplayer.model.*;
import chan.alfred.sketchplayer.*;
import java.awt.*;
import java.util.concurrent.*;
/*
public class SliderView extends View{
	private TimeSlider slider = new TimeSlider();
	private ScheduledFuture<?> future;
	private final SketchModel model;
	private ScheduledExecutorService executor;

	private Button play;
	private boolean playing = false;
	private boolean controlPressed = false;
	

	public SliderView(Context context, AttributeSet attrs){
		super(context,attrs);
		this.model = MainActivity.model;
		this.executor = MainActivity.executor;

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
		slider.setProgress(val);
	}

	public int getValue(){
		return slider.getProgress();
	}

	private class TimeSlider extends SeekBar{
		TimeSlider(Context context, AttributeSet attrs){
			super(context,attrs);
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
*/
