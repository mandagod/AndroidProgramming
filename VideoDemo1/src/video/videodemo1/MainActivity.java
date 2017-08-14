package video.videodemo1;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the layout from activity_main.xml
		setContentView(R.layout.activity_main);
		
	    // Locate the button in activity_main.xml
	    button = (Button) findViewById(R.id.streamButton);
	    
	    // Listen to button clicks
	    button.setOnClickListener(new OnClickListener() {
	      public void onClick(View arg0) {
	        // Start New Activity.class
	        Intent intent = new Intent(MainActivity.this, VideoViewActivity.class);
	        startActivity ( intent );
	      }
		 });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
