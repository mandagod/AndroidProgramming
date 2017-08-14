package example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class MainActivity extends Activity implements View.OnClickListener {

  EditText t1;
  EditText t2;

  Button plus;
  Button minus;
  Button multiply;
  Button divide;

  TextView displayResult;

  String oper = "";
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    // find the EditText elements (defined in res/layout/activity_main.xml
	    t1 = (EditText) findViewById(R.id.t1);
	    t2 = (EditText) findViewById(R.id.t2);

	    plus = (Button) findViewById(R.id.plus);
	    minus = (Button) findViewById(R.id.minus);
	    multiply = (Button) findViewById(R.id.multiply);
	    divide = (Button) findViewById(R.id.divide);

	    displayResult = (TextView) findViewById(R.id.displayResult);

	    // set  listeners
	    plus.setOnClickListener( this );
	    minus.setOnClickListener( this);
	    multiply.setOnClickListener( this);
	    divide.setOnClickListener( this);		
	}
	
	 // @Override
	  public void onClick( View view ) {
	    double num1 = 0;
	    double num2 = 0;
	    double result = 0;

	    // check if the fields are empty
	    if (TextUtils.isEmpty(t1.getText().toString())
	        || TextUtils.isEmpty(t2.getText().toString())) {
	      return;
	    }

	    // read EditText and fill variables with numbers
	    num1 = Float.parseFloat(t1.getText().toString());
	    num2 = Float.parseFloat(t2.getText().toString());

	    // perform operations
	    // save operator in oper for later use
	    switch ( view.getId() ) {
	    case R.id.plus:
	      oper = "+";
	      result = num1 + num2;
	      break;
	    case R.id.minus:
	      oper = "-";
	      result = num1 - num2;
	      break;
	    case R.id.multiply:
	      oper = "*";
	      result = num1 * num2;
	      break;
	    case R.id.divide:
	      oper = "/";
	      result = num1 / num2;
	      break;
	    default:
	      break;
	    }

	    // form the output line
	    displayResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
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
