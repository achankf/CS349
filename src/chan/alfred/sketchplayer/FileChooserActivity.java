package chan.alfred.sketchplayer;

import java.io.File;
import java.io.FilenameFilter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FileChooserActivity extends Activity {

	String path = "/";
	TextView tvv;

	private class FileBox extends TextView {
		private File file;

		public FileBox(Context context, File filee) {
			super(context);
			this.file = filee;
			
			if (file.isDirectory()){
				setTypeface(null, Typeface.BOLD);
			}

			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (file.isDirectory()) {
						if (!file.canExecute())
							return;
						path = file.getAbsolutePath();
						refresh();
						return;
					}
					Intent pathBack = new Intent();
					pathBack.putExtra("path", FileBox.this.file.toString());
					setResult(RESULT_OK, pathBack);
					finish();
				}
			});
		}
	}

	ViewGroup viewGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);

		viewGroup = (ViewGroup) findViewById(R.id.file_layout);
		refresh();
	}

	private void refresh() {
		File file = new File(path);
		try {
			File files[] = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					File temp = new File(dir + "/" + name);
					return name.length() > 0
							&& name.charAt(0) != '.'
							&& (temp.isDirectory() || name.toLowerCase()
									.endsWith("xml"));
				}
			});
			viewGroup.removeAllViews();

			{
				String temp = file.getParent();
				File prev;
				if (temp == null) {
					prev = new File("/");
				} else {
					prev = new File(temp);
				}
				FileBox tv = new FileBox(this, prev);
				tv.setText("..");
				viewGroup.addView(tv);
			}
			if (files == null)
				return;

			for (File f : files) {
				FileBox tv = new FileBox(this, f);
				tv.setText(f.getName());
				viewGroup.addView(tv);
			}
		} catch (SecurityException se) {
			se.printStackTrace();
		}
	}
}