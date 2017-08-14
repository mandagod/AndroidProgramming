package data.displayasset;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final AssetManager mgr = getAssets();
		displayFiles(mgr, "");
	}
	
	void displayFiles (AssetManager mgr, String path) {
	  try {
            String list[] = mgr.list(path);
            if (list != null)
              for (int i=0; i<list.length; ++i)
              {                 
                 if ( path.length() >  0 ) {
               	   Log.v("Assests:", path + "/" + list[i]);
                   displayFiles(mgr, path  + "/" + list[i] );
                  }else{
                   Log.v("Assests:", path  + list[i]);
                   displayFiles(mgr, path  + list[i] );
                 }
              }
            else
              Log.v("List:", "empty!");
       } catch (IOException e) {
             Log.v("List error:", "can't list" + path);
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
