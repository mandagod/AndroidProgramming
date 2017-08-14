package comm.calcclient;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener  {

	  public static double result = 0;
	  public static String oper = "";
	  public static String nums1;
	  public static String nums2;
		
	  private String str = "";
	  private int displayCount = 0;
	  EditText t1, t2;
	  ImageButton plusButton, minusButton, multiplyButton, didvideButton;
	  TextView displayResult;
	
	  /** Called when the activity is first created. */  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    // find elements defined in res/layout/activity_main.xml
	    t1 = (EditText) findViewById(R.id.t1);
	    t2 = (EditText) findViewById(R.id.t2);
	    plusButton = (ImageButton) findViewById(R.id.plusButton);
	    minusButton = (ImageButton) findViewById(R.id.minusButton);
	    multiplyButton = (ImageButton) findViewById(R.id.multiplyButton);
	    didvideButton = (ImageButton) findViewById(R.id.divideButton);
	    displayResult = (TextView) findViewById(R.id.displayResult);

	    // set listeners
	    plusButton.setOnClickListener(this);
	    minusButton.setOnClickListener(this);
	    multiplyButton.setOnClickListener(this);
	    didvideButton.setOnClickListener(this);
	}
	
	
	// @Override
	  public void onClick(View view) {
	    // check if the fields are empty
	    if (TextUtils.isEmpty(t1.getText().toString())
	          || TextUtils.isEmpty(t2.getText().toString())) 
	      return;
		
	    // read numbers from EditText 		
	    nums1 = t1.getText().toString();
	    nums2 = t2.getText().toString();
	    
	    // determine which image button has been clicked
	    switch (view.getId()) {
	      case R.id.plusButton:
	        oper = "+";
	        break;
	      case R.id.minusButton:
	        oper = "-";
	        break;
	      case R.id.multiplyButton:
	        oper = "*";
	        break;
	      case R.id.divideButton:
	        oper = "/";
	        break;
	      default:
	        break;
	    }
	    // Create thread to send request 
	    Client client = new Client ( nums1, nums2, oper );
	    Thread sendThread = new Thread ( client );
	    sendThread.start();
	    try {
	      result = client.getResult();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    // form the output line, display at most 4 lines on screen
	    if ( displayCount < 4 ){
	      str += nums1 + " " + oper + " " + nums2 + " = " + result + "\n";
	      displayCount++;
	    } else {
	      displayCount = 1;	
	      str = nums1 + " " + oper + " " + nums2 + " = " + result + "\n";
	    }
	    displayResult.setText( str );
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
