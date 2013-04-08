package chan.alfred.sketchplayer;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import chan.alfred.sketchplayer.model.SketchModel;

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
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();

		try {
			InputStream is = getAssets().open("sample.xml");
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			model.read(doc);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			tv.setText(sw.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		tv.setText(tv.getText() + " ret intent");
		switch (requestCode) {
		case ACTIVITY_CHOOSE_FILE: {
			if (resultCode == RESULT_OK) {
				tv.setText(tv.getText() + " intent ok");
				Uri uri = data.getData();
				String filePath = uri.getPath();
				tv.setText(filePath);
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
		timer.scheduleAtFixedRate(task, 0, 33);
	}

	public void stopPlaying() {
		garbageCollectTimerTasks();
		playButton.setText(getResources().getString(R.string.play));
		playing = false;
		model.updateAllViews();
	}
}
