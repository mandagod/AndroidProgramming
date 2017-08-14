package comm.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	  private ServerSocket serverSocket;
	  private String str;
	  Handler textHandler;
	  Thread serverThread = null;
	  private TextView textView;
	  public static final int portNumber = 1989;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.text1);
		textHandler = new Handler();
	    new Thread(new ServerThread()).start();
	}
	
	  @Override
	  protected void onStop() {
	    super.onStop();
	    try {
	      serverSocket.close();	        
	    } catch (IOException e) {
	      e.printStackTrace();
		}
	  }
	
	  //Inner class
	  class ServerThread implements Runnable {
	    public void run() {
	      Socket commSocket = null;
	      try {
		    serverSocket = new ServerSocket(portNumber);
		  } catch (IOException e) {
		    e.printStackTrace();
	      }
	      while (!Thread.currentThread().isInterrupted()) {
		    try {
		      commSocket = serverSocket.accept();
		      BufferedReader input = new BufferedReader (
		        new InputStreamReader( commSocket.getInputStream() ));
		      while ( ( str = input.readLine() )  != null ) 
		    	textHandler.post(new ShowText ());
	        } catch (IOException e) {
		       e.printStackTrace();
		    }
	      }
	    }
	  } //ServerThread
	  
	  // Inner class
	  class ShowText implements Runnable {	
	    @Override
		public void run() {
		  textView.setText(textView.getText().toString()+"Client said: "+ str + "\n");
		}
	  } //ShowText  

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
