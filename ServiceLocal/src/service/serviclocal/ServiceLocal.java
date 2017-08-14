package service.serviclocal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceLocal extends Service {
	  
	   private int counter = 0;	
	   public class LocalBinder extends Binder {
	       ServiceLocal getService() {
	           return ServiceLocal.this;
	       }
	   }

	   int getCount()
	   {
		   // Some trivial task
		   counter++;
		   return counter;
	   }
	   
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
	       Log.i("ServiceLocal", "Received start id " + startId + ": " + intent);
	       //Return sticky, so that this service  continues to run until it is explicitly stopped.
	       return START_STICKY;
	   }
	   
	   @Override
	   public void onDestroy() {
	       // Flash stopping message.
	       Toast.makeText(this, "Local Service Stopped", Toast.LENGTH_SHORT).show();
	   }


	   @Override
	   public IBinder onBind(Intent intent) {
	       return iBinder;
	   }
	   
	   // This object interacts with clients. 
	   private final IBinder iBinder = new LocalBinder();
}
