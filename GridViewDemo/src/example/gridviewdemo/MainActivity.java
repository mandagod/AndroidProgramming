package example.gridviewdemo;

import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this));
		
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	          Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
	        }
	      });
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

class ImageAdapter extends BaseAdapter {
	private Context context;
	// reference the images
	private Integer[] imageIds = {
			R.drawable.sample2, R.drawable.sample3,
			R.drawable.sample4, R.drawable.sample5,
			R.drawable.sample6, R.drawable.sample7,
			R.drawable.sample0, R.drawable.sample1,
			R.drawable.sample2, R.drawable.sample3,
			R.drawable.sample4, R.drawable.sample7,
	};
	public ImageAdapter(Context context0) {
		context = context0;
	}

	public int getCount() {
	    return imageIds.length;
	}

	public Object getItem(int position) {
	   return null;
	}

	public long getItemId(int position) {
	   return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
	    ImageView imageView;
	    if (convertView == null) {  // if it's not started, initialize some attributes
			  imageView = new ImageView(context);
			  imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
			  imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			  imageView.setPadding(6, 6, 6, 6);
	    } else {
	    	  imageView = (ImageView) convertView;
	    }
	    imageView.setImageResource(imageIds[position]);
	    return imageView;
	}	
}
