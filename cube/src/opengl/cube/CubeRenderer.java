package opengl.cube;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.SystemClock;
import android.view.MotionEvent;
  
public class CubeRenderer implements GLSurfaceView.Renderer {
   
    public float angle = 0.0f;
    private Cube cube = new Cube();
    //Square is not used here, you comment out cube and uncomment square
    // statements to experiment with it
	private Square square = new Square();
	private Context context;
	            
    public CubeRenderer ( Context context0 )
    {
	   context = context0;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color to grey
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        // Do not render back faces
        gl.glEnable( GL10.GL_CULL_FACE );
        gl.glCullFace ( GL10.GL_BACK );
        //square.initTexture(gl, context);
        cube.initTexture(gl, context);
        gl.glEnable(GL10.GL_TEXTURE_2D); 
    }
    
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();   // Reset the matrix to identity matrix
          
        // Move objects away from view point to observe
        gl.glTranslatef(-1.0f, 2.0f, -10.0f);
        // Rotate about a diagonal of cube 
        gl.glRotatef(angle, 1.0f, 1.0f, 0.0f);
        // Draw the cube    
        cube.draw(gl);
        gl.glLoadIdentity();	//reset matrix, draw another cube
        gl.glTranslatef(0.0f, 0.0f, -10.0f); 
        gl.glRotatef(angle, 0.0f, 1.0f, 1.0f);
        cube.draw(gl);
        gl.glLoadIdentity();	//reset matrix, draw another cube
        gl.glTranslatef(1.0f, -2.0f, -10.0f); 
        gl.glRotatef(angle, -1.0f, -1.0f, 0.0f);
        cube.draw(gl);

    //  square.draw( gl );
      
        gl.glLoadIdentity();   // Reset matrix                                             
     }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();  // Reset projection matrix
        // Setup viewing volume
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();  // Reset transformation matrix
    }
}

class Cube {
  //6 faces
  private final int nFaces = 6;

  private FloatBuffer vertexBuffer[] = new FloatBuffer[nFaces];
  private FloatBuffer textureBuffer[] = new FloatBuffer[nFaces];  // buffer holding the texture coordinates
  private int[] texHandles = new int[nFaces];
	  
  // Coordinates of 6 cube faces 
  private float vertices[][] = {
	 { -1.0f, -1.0f, 1.0f,  1.0f, -1.0f,  1.0f,
	   -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  1.0f},     //front                    
	 {  1.0f, -1.0f,-1.0f, -1.0f, -1.0f, -1.0f,
	    1.0f,  1.0f,-1.0f, -1.0f,  1.0f, -1.0f},     //back
	 { -1.0f, -1.0f,-1.0f, -1.0f, -1.0f,  1.0f, 
       -1.0f,  1.0f,-1.0f, -1.0f,  1.0f,  1.0f},     //left 
     {  1.0f, -1.0f, 1.0f,  1.0f, -1.0f, -1.0f, 
        1.0f,  1.0f, 1.0f,  1.0f,  1.0f, -1.0f},     //right                           
     { -1.0f, -1.0f,-1.0f,  1.0f, -1.0f, -1.0f,
       -1.0f, -1.0f, 1.0f,  1.0f, -1.0f,  1.0f},     //bottom                    
     { -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  1.0f,
       -1.0f,  1.0f, -1.0f, 1.0f,  1.0f, -1.0f} };   //top
	                     
  private float texture[][] = {         
	 // Mapping texture coordinates for the vertices
	 { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f},
     { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f},
     { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f},
     { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f},
     { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f},
     { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f} };

     public Cube() {
	   // initialize vertex Buffer for each  cube face  
	   // argument=(# of coordinate values * 4 bytes per float)
	   for ( int i = 0; i < nFaces; i++ )
	   {	      
	       ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices[i].length * 4);
	       byteBuf.order(ByteOrder.nativeOrder());
	       // create a floating point buffer from the ByteBuffer
	       vertexBuffer[i] = byteBuf.asFloatBuffer();
	       // add the coordinates to the FloatBuffer
	       vertexBuffer[i].put(vertices[i]);
	       // set the buffer to read the first vertex coordinates 
	       vertexBuffer[i].position(0);
	
           byteBuf = ByteBuffer.allocateDirect(texture[i].length * 4);
           byteBuf.order(ByteOrder.nativeOrder());
           textureBuffer[i] = byteBuf.asFloatBuffer();
           textureBuffer[i].put(texture[i]);
           textureBuffer[i].position(0);
	   }
     }
	    
     public void initTexture(GL10 gl, Context context) {
		// loading texture
		// generate 6 texture pointers
		gl.glGenTextures(nFaces, texHandles, 0);
		// get resource ids of images in res/drawable-hdpi
		int rids[] = new int[nFaces];  //resource ids
		// image file names
		String img[] = {"catherine", "lu",  "galaxy",  "lu1", "lu2", "zhouxun"};
		// Initialize texture feature for 6 (nFaces) faces
		for ( int i = 0; i < nFaces; i++ ){
		  rids[i] = context.getResources().getIdentifier( img[i] , "drawable", context.getPackageName());
                  // loading texture
                  Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), rids[i] );
		  gl.glBindTexture(GL10.GL_TEXTURE_2D, texHandles[i]);
		  // create nearest filtered texture
		  gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		  gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		  // Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		  GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		  //cleanup
		  bitmap.recycle();
	       }
		}

	    // Typical drawing routine using vertex array
     public void draw(GL10 gl) { 
	    // Vertices of a front face are in counterclockwise order 
	    gl.glFrontFace(GL10.GL_CCW);
	            
	    for ( int i = 0; i < nFaces; i++) {
          gl.glBindTexture(GL10.GL_TEXTURE_2D, texHandles[i]);
          gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer[i]);         
          gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer[i]);
          gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
          gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	         
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices[i].length / 3 ); 
          gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
          gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    }
	 }
}
  
class Square {
  private FloatBuffer vertexBuffer;   // buffer holding the vertices
  private FloatBuffer textureBuffer;  // buffer holding the texture coordinates
  private float vertices[] = {
	            -1.0f, -1.0f,  1.0f,        // V0 - bottom left
	            1.0f, -1.0f,  1.0f,        // V1 - bottom right
	            -1.0f,  1.0f,  1.0f,       // V2 - top left
	            1.0f,  1.0f,  1.0f         // V3 - top right
	    };

  private float texture[] = {         
          // Mapping coordinates for the vertices
	       0.0f, 1.0f,             // bottom left     (t0)  
	       1.0f, 1.0f,             // bottom right    (t1)
	       0.0f, 0.0f,             // top left        (t2)
	       1.0f, 0.0f,             // top right       (t3)
  };
  private int[] texHandles = new int[1];

  public Square() {
	  
	      ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); 	  
	      byteBuffer.order(ByteOrder.nativeOrder());
	      vertexBuffer = byteBuffer.asFloatBuffer();	  
	      vertexBuffer.put(vertices);
	      vertexBuffer.position(0);
	      
	      byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
	      byteBuffer.order(ByteOrder.nativeOrder());
	      textureBuffer = byteBuffer.asFloatBuffer();
	      textureBuffer.put(texture);
	      textureBuffer.position(0);
	  }
  
  public void initTexture(GL10 gl, Context context) {
	    // loading texture
	    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
	            R.drawable.catherine);
	    // generate one texture pointer
	    gl.glGenTextures(1, texHandles, 0);
	    // ...and bind it to our array
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, texHandles[0]);
	    // create nearest filtered texture
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	    // Use Android GLUtils to specify a two-dimensional texture image from our bitmap
	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	    // Clean up
	    bitmap.recycle();
	}

  
  public void draw(GL10 gl) { 
      // Vertices of a front face are in counterclockwise order 
      gl.glFrontFace(GL10.GL_CCW);
      gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
      // bind the previously generated texture
       gl.glBindTexture(GL10.GL_TEXTURE_2D, texHandles[0]);

      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
      gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
      
      gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);       
               
       gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
       gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
   }
}
