package comm.calcclient;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable {
  private static Socket socket;
  private static String nums1;
  private static String nums2;
  private static String calcOp;
  private static double result = 0;
  final Lock mutex = new ReentrantLock();
  final Condition resultReady = mutex.newCondition();
  private int count = 0;
  
  public Client ( String n1, String n2, String op )
  {
    nums1 = n1;
    nums2 = n2;
    calcOp  = op;
  }
  @Override
  public void run() {
    // TODO Auto-generated method stub

    String serverAddr = "192.168.1.69";        
    int portNumber = 1989;

    mutex.lock();
    try {
      //socket = initiateContact(serverAddr, portNumber);
    	
      remoteCalculation( serverAddr, portNumber );
      count++;
      resultReady.signal();
    } catch (UnknownHostException e1) {
        e1.printStackTrace();
    } catch (IOException e1) {
        e1.printStackTrace();
	} finally{
      mutex.unlock();
    }
  }

  public  void remoteCalculation( String serverAddr, int portNumber ) 
		   throws IOException 
  {
    Socket socket = null;
    socket = new Socket(serverAddr, portNumber);
 
    BufferedReader stdIn = new BufferedReader(new InputStreamReader( System.in));
    // Form JSON object string
    String jsonString = "{" + "\"calcOp\":\"" 
        + calcOp + "\"," + "\"op1\":\""
        + nums1 + "\"," + "\"op2\":\""
        + nums2 + "\"}";

    System.out.println( jsonString );
	sendMessage( socket, jsonString);
    String serverReply = receiveMessage( socket );
    System.out.println("Result: " + serverReply);
    result = Double.parseDouble((String) serverReply);
    stdIn.close();
    socket.close();
  }
    
  public double getResult()  throws InterruptedException
  {
    mutex.lock();
    System.out.println("getResult wait");
    // The count is to ensure the function won't wait forever 
    //   if signal has been issued before  await.
    while ( count == 0 )
      resultReady.await();   // Condition wait
    count = 0;
    mutex.unlock();
    
    return result;
  }

	

  // Receive message from a socket 

  public static String receiveMessage(Socket socket) 
		                          throws IOException {
    String inputLine = null;
    BufferedReader inputBuffer = null;
    inputBuffer = new BufferedReader( new InputStreamReader
                                          ( socket.getInputStream()));
    inputLine = inputBuffer.readLine();

    return inputLine;
  }

	
   // send message to socket 
  
  public static void sendMessage(Socket socket, String outputLine)
        throws IOException {
    PrintWriter outputWriter = null;
    outputWriter = new PrintWriter(socket.getOutputStream(), true);
    outputWriter.println(outputLine);
  }
}
