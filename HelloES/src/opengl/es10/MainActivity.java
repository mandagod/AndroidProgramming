package opengl.es10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	
	private GLSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
		mGLView = new HelloESSurfaceView(this);
		setContentView(mGLView);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// The following call pauses the rendering thread.
		mGLView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// The following call resumes a paused rendering thread.
		mGLView.onResume();
	}
	
	
	class HelloESSurfaceView extends GLSurfaceView {
		private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
		private HelloESRenderer renderer;
		private float previousX;
		private float previousY;
		
		public HelloESSurfaceView(Context context){
			super(context);
			
			// set the renderer member
			renderer = new HelloESRenderer();
			setRenderer(renderer);
			
			// Set the Renderer for drawing on the GLSurfaceView
			//setRenderer(new HelloESRenderer());
			
			// Render the view only when there is a change; onDrawFrame
			// is not called unless requestRender() is called explicitly
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent e) {
			// MotionEvent reports input details from the touch screen
			// and other input controls. Here, we are only interested
			// in events where the touch position has changed.
			float x = e.getX();
			float y = e.getY();
			switch (e.getAction()) {
				case MotionEvent.ACTION_MOVE:
				float dx = x - previousX;
				float dy = y - previousY;
				// reverse direction of rotation above the mid-line
				if (y > getHeight() / 2)
					dx = dx * -1 ;
		
				// reverse direction of rotation to left of the mid-line
				if (x < getWidth() / 2)
					dy = dy * -1 ;
				renderer.angle += (dx + dy) * TOUCH_SCALE_FACTOR;
				requestRender();
			}
			previousX = x;
			previousY = y;
			return true;
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
