package opengl.es10;

import java.nio.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;

public class HelloESRenderer implements GLSurfaceView.Renderer {
	private FloatBuffer triangle;
	public float angle = 0.0f;

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background frame color to blue
		gl.glClearColor(0.0f, 0.0f, 0.9f, 1.0f);
		
		// initialize the triangle vertex array
		initShapes();
		// Enable use of vertex arrays
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void onDrawFrame(GL10 gl) {
		// Redraw background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		// Set GL_MODELVIEW transformation mode
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity(); //reset the matrix to its default state
		
		// When using GL_MODELVIEW, you must set the view point.
		// camera at (0, 0, 5) look at (0,0,0), up = (0, 1, 0)
		GLU.gluLookAt(gl, 0, 0, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		SystemClock.sleep ( 1000 ); //delay for 1 second
		angle += 6; //increment angle by 6 degrees
		//rotate about z-axis for 30 degrees
		gl.glRotatef(angle, 0, 0, 1);
		//magnify triangle by x3 in y-direction
		gl.glScalef ( 1, 3, 1);
		
		// Draw the triangle using green color
		gl.glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangle);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		float aratio = (float) width / height; //aspect ratio
		float l, r, b, t, n, f; //left,right,bottom,top,near,far
		b = -1.5f; t = 1.5f; n = 3.0f; f = 7.0f;
		l = b * aratio; r = t * aratio;
		gl.glMatrixMode(GL10.GL_PROJECTION);//set projection mode
		gl.glLoadIdentity(); // reset the matrix
		gl.glFrustumf( l, r, b, t, n, f); //apply projection matrix
	}
	
	private void initShapes(){
		float vertices[] = { // (x, y, z) of triangle
		-0.6f, -0.5f, 0,
		0.6f, -0.5f, 0,
		0.0f, 0.5f, 0
		};
		// initialize vertex Buffer for triangle
		// argument=(# of coordinate values * 4 bytes per float)
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		// use the device hardware¡¯s native byte order
		vbb.order(ByteOrder.nativeOrder());
		// create a floating point buffer from the ByteBuffer
		triangle = vbb.asFloatBuffer();
		// add the coordinates to the FloatBuffer
		triangle.put(vertices);
		// set the buffer to read the first vertex coordinates
		triangle.position(0);
	}
}