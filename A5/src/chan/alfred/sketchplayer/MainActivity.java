package chan.alfred.sketchplayer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import chan.alfred.sketchplayer.model.*;

public class MainActivity extends Activity {
	final public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	final public static SketchModel model = new SketchModel();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
