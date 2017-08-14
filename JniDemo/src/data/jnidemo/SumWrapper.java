package data.jnidemo;

public class SumWrapper {
	// Declare native method public to expose it
	public static native int sum ( int n );
	public static int getSum ( int n) {
		int s = sum ( n ); //call native method
		return s;
	}
	// Load library
	static {
		System.loadLibrary( "sum-jni"); // C-file is sum-jni.c
	}
}
