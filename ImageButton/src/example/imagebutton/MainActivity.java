package example.imagebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	ImageButton imageButton;
	Button resetButton;
	TextView message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	public void addListenerOnButton() {
		 
		imageButton = (ImageButton) findViewById(R.id.imageButton1);
        resetButton = (Button) findViewById(R.id.resetButton);
		imageButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View view) {
 
			   message = (TextView) findViewById(R.id.message);
//			   Toast.makeText(MainActivity.this,
//				"Image Button clicked!", Toast.LENGTH_SHORT).show();
			   message.setText("Image Button Clicked!");
			}
 
		});

		resetButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View view) {
 
			   message = (TextView) findViewById(R.id.message);
			   message.setText(" ");
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
