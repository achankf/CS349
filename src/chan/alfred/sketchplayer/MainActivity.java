package chan.alfred.sketchplayer;

import java.io.File;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import chan.alfred.sketchplayer.model.SketchModel;

public class MainActivity extends Activity {
	final public static SketchModel model = new SketchModel();
	final int ACTIVITY_CHOOSE_FILE = 2;
	final int ACTIVITY_CONFIGURE = 1;
	private int blue = 0xff;
	private int fps = Config.DEFAULT_FPS;
	private int green = 0xff;
	private Button playButton, loadFile, config;
	private boolean playing = false;

	private int red = 0xff;
	private SeekBar seekBar;
	private TimerTask task = null;
	private Timer timer = new Timer();

	public void garbageCollectTimerTasks() {
		if (task == null)
			return;
		task.cancel();
		task = null;
		timer.purge();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTIVITY_CHOOSE_FILE:
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				String filePath = uri.getPath();
				DocumentBuilderFactory docFactory = DocumentBuilderFactory
						.newInstance();

				try {
					DocumentBuilder docBuilder = docFactory
							.newDocumentBuilder();
					Document doc = docBuilder.parse(new File(filePath));
					model.read(doc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case ACTIVITY_CONFIGURE:
			if (resultCode == RESULT_OK) {
				fps = data.getIntExtra("fps", Config.DEFAULT_FPS);
				red = data.getIntExtra("red", 0xff);
				blue = data.getIntExtra("blue", 0xff);
				green = data.getIntExtra("green", 0xff);
				this.getWindow().getDecorView().setBackgroundColor(
						Color.rgb(red, blue, green));
			}
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		loadFile = (Button) findViewById(R.id.load_file);
		playButton = (Button) findViewById(R.id.play_switch);
		config = (Button) findViewById(R.id.config);
		seekBar = (SeekBar) findViewById(R.id.seekBar);

		seekBar.setMax(1000);

		setupListeners();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();

		try {
			InputStream is = getAssets().open("sample.xml");
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			model.read(doc);
		} catch (Exception e) {
			e.printStackTrace();
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
			@Override
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

		config.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				garbageCollectTimerTasks();
				Intent configure = new Intent(MainActivity.this,
						ConfigActivity.class);

				configure.putExtra("fps", fps);
				configure.putExtra("red", red);
				configure.putExtra("blue", blue);
				configure.putExtra("green", green);
				startActivityForResult(configure, ACTIVITY_CONFIGURE);
			}
		});

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				model.setFrame(seekBar.getProgress());
				model.updateAllViews();
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
					@Override
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
		timer.scheduleAtFixedRate(task, 0, 1000 / fps);
	}

	public void stopPlaying() {
		garbageCollectTimerTasks();
		playButton.setText(getResources().getString(R.string.play));
		playing = false;
		model.updateAllViews();
	}
}
