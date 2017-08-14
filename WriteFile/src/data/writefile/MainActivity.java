package data.writefile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
		
      try { 
          final String helloString = new String("Hello, Friend!");
          FileOutputStream fos = openFileOutput("sampleFile.txt", MODE_WORLD_READABLE);
          OutputStreamWriter osw = new OutputStreamWriter( fos ); 

          // Write the string to the file
          osw.write ( helloString );
                         
          // Flush out anything in buffer
          osw.flush();  
          osw.close();
          // Read the file back...

          FileInputStream fis = openFileInput( "sampleFile.txt" );
          InputStreamReader isr = new InputStreamReader( fis );

          // Prepare a char-Array that to read data back
          char[] inputBuffer = new char[fis.available()];

          // Fill the Buffer with data from the file
          isr.read(inputBuffer);

          // Transform the chars to a String
          String readString = new String(inputBuffer);

          // Check if we read back the same chars that we had written out
          boolean writeReadEqual = helloString.equals ( readString );
          Log.i( "String read:", readString );
          Log.i( "File Reading:", "success = " + writeReadEqual );

        } catch (IOException ioe)  { ioe.printStackTrace(); }
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
