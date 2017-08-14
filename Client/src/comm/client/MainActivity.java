package comm.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private Socket socket;
	private static final int PORT_NO = 1989;
	// Need to change IP address to the IP of your server
	//private static final String SERVER_IP = "192.168.2.100";
	private static final String SERVER_IP = "192.168.2.102";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread(new ClientThread()).start();
	}
	
	  public void onClick(View view) {
			try {
		      EditText editText = (EditText) findViewById(R.id.EditText1);
		      String str = editText.getText().toString();
		      PrintWriter out = new PrintWriter(new BufferedWriter(
		      new OutputStreamWriter(socket.getOutputStream())), true);
		      out.println(str);
		    } catch (UnknownHostException e) {
		      e.printStackTrace();
			} catch (IOException e) {
			  e.printStackTrace();
			} catch (Exception e) {
			  e.printStackTrace();
			}
	  }
	  
	  // inner class
	  class ClientThread implements Runnable {
	    @Override
	    public void run() { 
		  try {
		    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
		    socket = new Socket(serverAddr, PORT_NO );
		  } catch (UnknownHostException e) {
		    e.printStackTrace();
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
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
