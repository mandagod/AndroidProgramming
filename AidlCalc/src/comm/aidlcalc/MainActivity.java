// MainActivity.java
package comm.aidlcalc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener
{
  private static final String TAG = "MainActivity";
  IRemoteService remoteService;
  RemoteServiceConnection remoteConnection;
  EditText t1;
  EditText t2;
  Button multiply;
  TextView displayResult;


  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bindActivityToService();

    // Setup the UI
    t1 = (EditText) findViewById(R.id.t1);
    t2 = (EditText) findViewById(R.id.t2);
    multiply = (Button) findViewById(R.id.multiply);
    displayResult = (TextView) findViewById(R.id.displayResult);
    multiply.setOnClickListener( this);    
  }

  public void onClick ( View view ) {
	 float num1 = 0, num2 = 0, product = 0;
	 // check if the fields are empty
     if (TextUtils.isEmpty(t1.getText().toString())
         || TextUtils.isEmpty(t2.getText().toString())) {
       return;
     }

     // read EditText and fill variables with numbers
     num1 = Float.parseFloat(t1.getText().toString());
     num2 = Float.parseFloat(t2.getText().toString());
     try {
         product = remoteService.multiply(num1, num2);
       } catch (RemoteException e) {
         Log.d(MainActivity.TAG, "onClick failed with: " + e);
         e.printStackTrace();
       }
     // form the output line
     displayResult.setText( num1 + " * " + num2 + " = " + product );
  }
  /**
   * This class implements the actual service connection. It casts the bound
   * stub implementation of the service to the AIDL interface.
   */
  class RemoteServiceConnection implements ServiceConnection 
  {
	// Called when the connection with the service is established
    public void onServiceConnected(ComponentName name, IBinder boundService) {
      remoteService = IRemoteService.Stub.asInterface((IBinder) boundService);
      Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG)
          .show();
    }

    // Called when connection with service disconnects unexpectel
    public void onServiceDisconnected(ComponentName name) {
      remoteService = null;
      Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_LONG)
          .show();
    }
  }

  /** Binds this activity to the service. */
  private void bindActivityToService() {
    remoteConnection = new RemoteServiceConnection();
    Intent intent = new Intent();
    intent.setClassName("comm.aidlcalc", comm.aidlcalc.RemoteService.class.getName());
    boolean ret = bindService(intent, remoteConnection, Context.BIND_AUTO_CREATE);
    Log.v(TAG, "Binding succesfully is " + ret);
  }

  // Unbinds this activity from the service. 
  private void releaseService() {
    unbindService(remoteConnection);
    remoteConnection = null;
    Log.v(TAG, "releaseService() unbound.");
  }

  // Called when the activity is about to terminate
  @Override
  protected void onDestroy() {
    releaseService();
  }
}