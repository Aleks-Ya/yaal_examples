#include <jni.h>
#include <stdio.h>
#include "jni_HelloWorld.h"

JNIEXPORT void JNICALL Java_jni_HelloWorld_printHello(JNIEnv *env, jobject obj) {
    printf("Hello, World from C!\n");
}