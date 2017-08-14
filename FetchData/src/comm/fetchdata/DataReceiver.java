package comm.fetchdata;

import android.R.layout;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DataReceiver extends BroadcastReceiver 
{
  @Override
  public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
    Bundle bundle = intent.getExtras();
    if (bundle != null) {
      String string = bundle.getString(FetchData.FPATH);
      int resultCode = bundle.getInt(FetchData.RESULT);
        if (resultCode == Activity.RESULT_OK) {
          Toast.makeText(context,
              "Fetch complete. Store URI: " + string,
	              Toast.LENGTH_LONG).show();
        } else {
          Toast.makeText(context, "Fetch failed",
              Toast.LENGTH_LONG).show();
        }
      }
    }
}


