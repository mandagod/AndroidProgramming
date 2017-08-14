#include <jni.h>
JNIEXPORT jint JNICALL Java_data_jnidemo1_SumWrapper_sum
  (JNIEnv * je, jclass jc, jint n)
{
  int i, sum = 0;

  for ( i = 1; i <= n; i++ )
    sum += i;

  return sum;
}
