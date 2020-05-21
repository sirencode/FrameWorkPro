//
// Created by shen yonghe on 2020/4/29.
//
#include <jni.h>
#include <stdio.h>
#include <HelloWorld.h>

JNIEXPORT void JNICALL Java_JniTest_print
  (JNIEnv *env, jobject obj)
{
    printf("Hello World!\n");
    return ;
}