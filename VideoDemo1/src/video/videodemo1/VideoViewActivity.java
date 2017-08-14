package video.videodemo1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {

	ProgressDialog progressDialog;
	VideoView videoView;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the layout from video_main.xml
		setContentView(R.layout.videoview_main);
		// Find your VideoView in video_main.xml layout
	    videoView = (VideoView) findViewById(R.id.videoView);
	    // Create a progressbar
	    progressDialog = new ProgressDialog(VideoViewActivity.this);
	    // Set progressbar title
	    progressDialog.setTitle("Video Streaming");
	    // Set progressbar message
	    progressDialog.setMessage("Loading...");
	    progressDialog.setIndeterminate ( false );
	    progressDialog.setCancelable ( false );
	    // Show progressbar
	    progressDialog.show();
	    
	    try {
	        // Create MediaController
	        MediaController mediaController = new MediaController(VideoViewActivity.this);
	        mediaController.setAnchorView(videoView);
	        //Note: omit extension here though the real filename has it
	        Uri video = Uri.parse ("android.resource://video.videodemo1/raw/android");
	        // If we want to play a video from a web site, we have
	        // to specify the full file name full URL path like the following
	        //String videoURL = "http://www.forejune.com/android/videos/android.3gp";
	        //Uri video = Uri.parse ( videoURL );
	        videoView.setMediaController ( mediaController );
	        videoView.setVideoURI(video);
	      } catch (Exception e) {
	        Log.e("Error", e.getMessage());
	        e.printStackTrace();
	      }
	    
	    videoView.requestFocus();
	    videoView.setOnPreparedListener(new OnPreparedListener() {
	    // Close the progress bar and play the video
	    public void onPrepared(MediaPlayer mp) {
	      progressDialog.dismiss();
	      videoView.start();
	    }
	   });
	}
}
