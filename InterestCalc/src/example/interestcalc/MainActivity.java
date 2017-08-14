//Interest Calculator
package example.interestcalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

//main Activity class for Interest Calculator
public class MainActivity extends Activity {

	private double principal; //amount entered by the user
	private double ratePercent; //inter rate in % set with SeekBar
	private EditText principalEditText; //user input for principal
	private TextView rateTextView; //displays rate percentage
	private TextView totalTextView;
	private TextView interestTextView; //displays interest amount
	
	//constants used in saving/restoring state
	private static final String PRINCIPAL = "PRINCIPAL";
	private static final String INTEREST_RATE = "INTEREST_RATE";
	
	// Called when the activity is first created.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);//call superclass¡¯s version
		setContentView(R.layout.activity_main);//inflate the GUI
		
		//check whether app just started or is being restored from memory
		if ( savedInstanceState == null ) // the app just started running
		{
			principal = 100.0; //initialize the principal to 100
			ratePercent = 3.5; //initialize the interest rate to 3.5%
		} else { // restore app from memory, not executed from scratch
			// restore saved values
			principal = savedInstanceState.getDouble(PRINCIPAL);
			ratePercent = savedInstanceState.getDouble(INTEREST_RATE);
		}
		
		// get the TextView displaying the rate percentage
		rateTextView = (TextView) findViewById(R.id.rateTextView);
		
		// get the interest and total TextView
		interestTextView=(TextView) findViewById(R.id.interestTextView);
		totalTextView = (TextView) findViewById(R.id.totalTextView);
		
		// get the principal editText
		principalEditText=(EditText)findViewById(R.id.principalEditText);
		
		// editTextWatcher handles editText¡¯s onTextChanged event
		principalEditText.addTextChangedListener(editTextWatcher);
		
		// get the SeekBar used to set interest rate
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(seekBarListener);
	}// end method onCreate
	
	// updates the interest and total EditTexts
	private void updates() {
		rateTextView.setText(String.format("%.02f %s",ratePercent,"%"));
		// calculate interest
		double interest = principal * ratePercent * .01;
		
		// calculate the total, including principal and interest
		double total = principal + interest;
		
		// display interest and total amounts
		interestTextView.setText( String.format("%.02f", interest));
		totalTextView.setText( String.format("%.02f", total));
	}
	
	// save values of editText and SeekBar
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putDouble(PRINCIPAL, principal);
		outState.putDouble(INTEREST_RATE, ratePercent);
	}
	
	// called when the user changes the position of SeekBar
	private OnSeekBarChangeListener seekBarListener =
					new OnSeekBarChangeListener() {
		// update ratePercent, then call updates
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser)
		{
			// sets ratePercent to position of the SeekBar¡¯s thumb
			ratePercent = seekBar.getProgress() /10.0;
			updates(); // update interest and total
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
		}		
	};// end OnSeekBarChangeListener
	
	// event-handling object that responds to editText¡¯s events
	private TextWatcher editTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start,
				int before, int count)
		{
			// convert editText¡¯s text to a double
			try
			{
				principal = Double.parseDouble(s.toString());
			}
			catch (NumberFormatException e)
			{
				principal = 0.0; // default if an exception occurs
			}
			updates(); // update the values
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start,
					int count, int after)
		{
		}
	};// end editTextWatcher

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
}// end class MainActivity
