package comm.fetchdata;

import java.io.*;
import java.net.URL;
import android.os.*;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.app.IntentService;

public class FetchData extends IntentService 
{
	  private int result = Activity.RESULT_CANCELED;
	  public static String URL_ADDRESS = "http://www.forejune.com";
	  public static String FNAME = "downloaded.html";
	  public static String FPATH = "./";
	  public static String RESULT = "result";
	  public static String NOTIFICATION = "comm.fetchdata.receiver";

	  public FetchData() {
	    super("FetchData");
	  }

	  // will be called asynchronously by Android
	  @Override
	  protected void onHandleIntent(Intent intent) {
	    String urlUsed = intent.getStringExtra( URL_ADDRESS );
	   	String fileName = intent.getStringExtra(FNAME);
	       
	    File output = new File(Environment.getExternalStorageDirectory(),
	        fileName);
	    if (output.exists()) {
	      output.delete();
	    }

	    InputStream is = null;
	    FileOutputStream fos = null;
	    Toast.makeText(FetchData.this, "Testing FetchData",
	            Toast.LENGTH_LONG).show();
	    try {
	      URL url = new URL ( urlUsed );
	      is = url.openConnection().getInputStream();
	      InputStreamReader isr = new InputStreamReader( is );
	      fos = new FileOutputStream(output.getPath());
	   
	      int next = -1;
	      while ((next = isr.read()) != -1) 
	        fos.write(next);
	   
	      result = Activity.RESULT_OK;

	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if ( is != null) {
	        try {
	          is.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	      if (fos != null) {
	          try {
	            fos.close();
	          } catch (IOException e) {
	            e.printStackTrace();
	          }
	      }
	    }
	    notifying(output.getAbsolutePath(), result);
	 }

	    private void notifying( String outputPath, int result ) {
	      Intent intent = new Intent(NOTIFICATION);
	      intent.putExtra(FPATH, outputPath);
	      intent.putExtra(RESULT, result);
	      sendBroadcast(intent);
	    }
}
