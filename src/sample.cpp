#include "sample.h"
#include <string.h>
#include <iostream>

using namespace std;

JNIEXPORT void JNICALL Java_sample_helloFromC
  (JNIEnv *, jobject){
	cout << "in C++ cod! :)" << endl;	
  }
