package data.readraw;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String LOG_APP_TAG = "tag";
		String str = null;
		InputStream inputStream = null;		 
		try {
		   inputStream = getResources().openRawResource(R.raw.hello);
		   byte[] reader = new byte[inputStream.available()];;
		   while (inputStream.read(reader) != -1) {}
		   str = new String ( reader );
		   System.out.println ( str );
		   Log.d(LOG_APP_TAG, str);
		 }  catch(IOException e) {
            Log.e(LOG_APP_TAG, e.getMessage());
         }
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
