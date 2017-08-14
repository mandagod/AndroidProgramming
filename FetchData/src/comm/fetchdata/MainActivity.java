package comm.fetchdata;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	public TextView textView;
	DataReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
          receiver = new DataReceiver ();
          textView = (TextView) findViewById(R.id.status);
	}
	
	  @Override
	  protected void onResume() {
	    super.onResume();
	    registerReceiver(receiver, new IntentFilter(FetchData.NOTIFICATION));
	  }

	  @Override
	  protected void onPause() {
		    super.onPause();
		    unregisterReceiver(receiver);
		  }

	  public void onClick(View view) {
		    Intent intent = new Intent(this, FetchData.class);
		    // add info for the service of fetching file 
		    intent.putExtra(FetchData.FNAME, "downloaded.txt");
		    intent.putExtra(FetchData.URL_ADDRESS,
		        "http://www.forejune.com/index.html");
		    startService(intent);
		    textView.setText("Service started");
	}
}
