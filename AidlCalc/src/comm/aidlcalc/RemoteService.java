//RemoteService.java
package comm.aidlcalc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service 
{
  private static final String TAG = "RemoteService";
  
  @Override
  public void onCreate() {
    super.onCreate();
    Log.v(TAG, "onCreate()");
  }
  
  @Override
  public IBinder onBind(Intent intent) {

    return new IRemoteService.Stub() {      
       // Implement  multiply() 
      public float multiply (float a, float b) 
    		  throws RemoteException {
        Log.v(TAG, String.format("RemoteService.add(%f, %f)", a, b));
        return a * b;
      }
    };
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.v(TAG, "onDestroy()");
  }
}