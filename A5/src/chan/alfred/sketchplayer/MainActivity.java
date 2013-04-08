package chan.alfred.sketchplayer;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import chan.alfred.sketchplayer.model.*;

public class MainActivity extends Activity {
	final public static SketchModel model = new SketchModel();
	private Button playButton, loadFile;
	private boolean playing = false;
	private SeekBar seekBar;
	private TimerTask task = null;
	private Timer timer = new Timer();
	final int ACTIVITY_CHOOSE_FILE = 1;

	private TextView tv;

	public void garbageCollectTimerTasks() {
		if (task == null)
			return;
		task.cancel();
		task = null;
		timer.purge();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadFile = (Button) findViewById(R.id.load_file);
		playButton = (Button) findViewById(R.id.play_switch);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		tv = (TextView) findViewById(R.id.debug_hint);

		seekBar.setMax(1000);

		setupListeners();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY_CHOOSE_FILE: {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				String filePath = uri.getPath();
				tv.setText(filePath);
				try {
					model.read(new DataInputStream(new FileInputStream(filePath)));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setupListeners() {
		loadFile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent chooseFile;
				Intent intent;
				chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
				chooseFile.setType("*/*");
				intent = Intent.createChooser(chooseFile, "Choose a file");
				startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
			}
		});

		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Boolean temp = playing;
				model.resetAllViews();
				if (!temp) {
					startPlaying();
				} else {
					stopPlaying();
				}

			}
		});

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				model.setFrame(seekBar.getProgress());
				model.updateAllViews();
				tv.setText(Integer.toString(seekBar.getProgress()));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}

	public void startPlaying() {
		playButton.setText(getResources().getString(R.string.stop));
		playing = true;

		garbageCollectTimerTasks();

		task = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						int frame = seekBar.getProgress();
						if (frame >= seekBar.getMax()) {
							stopPlaying();
							return;
						}
						seekBar.setProgress(frame + 1);
					}
				});
			}
		};
		timer.scheduleAtFixedRate(task, 0, 33);
	}

	public void stopPlaying() {
		garbageCollectTimerTasks();
		playButton.setText(getResources().getString(R.string.play));
		playing = false;
		model.updateAllViews();
	}
}
