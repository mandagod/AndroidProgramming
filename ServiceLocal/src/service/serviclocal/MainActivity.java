package service.serviclocal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ServiceLocal boundService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent= new Intent(this, ServiceLocal.class);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}
	
	private ServiceConnection connection = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name,
				IBinder service){
			boundService =
				((ServiceLocal.LocalBinder)service).getService();
			Toast.makeText(MainActivity.this, "Service connected!",
									Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			boundService = null;
			Toast.makeText(MainActivity.this, "Service disconnected",
					Toast.LENGTH_SHORT).show();
		}
	};
	
	public void onClick(View view) {
		  if ( boundService != null ) {
			Toast.makeText(this, "Service bound " + boundService.getCount(), Toast.LENGTH_LONG).show();
		  } else 
			Toast.makeText(this, "Service not bound ", Toast.LENGTH_SHORT).show();
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
