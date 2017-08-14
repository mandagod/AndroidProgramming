package data.jnidemo1;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText n;
	private TextView sum;
	private Button getsum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		n = (EditText) findViewById(R.id.n); 
		getsum = (Button) findViewById(R.id.getsum);
		sum = (TextView) findViewById(R.id.sum);
		TextView info = (TextView) findViewById(R.id.info);
	}
	
	// @Override
	public void onClick(View view) {
	    // check if the fields are empty
	   if (TextUtils.isEmpty(n.getText().toString()) )
	      return;

	    // read numbers from EditText               
	   String str = n.getText().toString();
	   
	   int n1 = Integer.parseInt( str );
	   int s = SumWrapper.getSum( n1 );
	   
	   // display result
	   str = "Sum of 1 to " + str + " is ";
	   str += Integer.toString ( s );;
	   sum.setText ( str );
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
