package chan.alfred.sketchplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ConfigActivity extends Activity {

	private int red, blue, green, fps;
	private SeekBar seekFPS, seekRed, seekBlue, seekGreen;
	private TextView tvFPS, tvRed, tvBlue, tvGreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		Intent intent = getIntent();
		fps = intent.getIntExtra("fps", Config.DEFAULT_FPS) - Config.FPS_MIN;
		red = intent.getIntExtra("red", 0xff);
		blue = intent.getIntExtra("blue", 0xff);
		green = intent.getIntExtra("green", 0xff);

		Button apply = (Button) findViewById(R.id.apply);
		seekFPS = (SeekBar) findViewById(R.id.fps_seekbar);
		seekRed = (SeekBar) findViewById(R.id.red_seekbar);
		seekBlue = (SeekBar) findViewById(R.id.blue_seekbar);
		seekGreen = (SeekBar) findViewById(R.id.green_seekbar);
		tvFPS = (TextView) findViewById(R.id.current_fps);
		tvRed = (TextView) findViewById(R.id.current_red);
		tvBlue = (TextView) findViewById(R.id.current_blue);
		tvGreen = (TextView) findViewById(R.id.current_green);

		seekFPS.setProgress(fps);
		seekRed.setProgress(red);
		seekBlue.setProgress(blue);
		seekGreen.setProgress(green);
		updateViews();

		seekFPS.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				fps = seekBar.getProgress();
				updateViews();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		seekRed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				red = seekBar.getProgress();
				updateViews();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		seekBlue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				blue = seekBar.getProgress();
				updateViews();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		seekGreen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				green = seekBar.getProgress();
				updateViews();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		apply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent configBack = new Intent();
				configBack.putExtra("fps",fps+Config.FPS_MIN);
				configBack.putExtra("red",red);
				configBack.putExtra("blue",blue);
				configBack.putExtra("green",green);
				setResult(RESULT_OK, configBack);
				finish();
			}
		});
	}
	

	private void updateViews() {
		tvFPS.setText(Integer.toString(fps+Config.FPS_MIN));
		tvRed.setText(Integer.toString(red));
		tvBlue.setText(Integer.toString(blue));
		tvGreen.setText(Integer.toString(green));
		this.getWindow().getDecorView().setBackgroundColor(
				Color.rgb(red, blue, green));
	}
}
