package stt.sttdemo0;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * A very simple SR application that starts speech recognition
 * activity through the use of RecognizerIntent.
 * Spoken words are convert to text and saved as a list of words. 
 */
public class MainActivity extends Activity {

	private static final int REQUEST_CODE = 1989;
	private ListView words;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        Button speechButton = (Button) findViewById(R.id.speechbutton);
        
        words = (ListView) findViewById(R.id.listview);
     
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        
        // Disable button in the absence of recognition service
        if ( activities.size() == 0 ) {
            speechButton.setEnabled ( false );
            speechButton.setText ( "Recognizer not present" );
        }
	}
	
    public void onClick( View view )
    {
        startSpeechRecognition();
    }
 
    // Start the speech recognition activity through an intent.
    private void startSpeechRecognition()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUEST_CODE);
    }
 
    // Handle the results from the speech recognition activity.
    @Override
    protected void onActivityResult(int requestCode, int result, Intent intent)
    {
        if (requestCode == REQUEST_CODE && result == RESULT_OK)
        {
            // Populate the list by words  recognized by recognition engine 
            ArrayList<String> matches = intent.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            words.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
        }
        super.onActivityResult(requestCode, result, intent);
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
