#include <jni.h>
#include <string.h>
#include "ffmpeg.h"

#ifdef Debug
#include <android/log.h>
#define LOG_TAG "来自jni:"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#endif


JNIEXPORT jstring

JNICALL
Java_com_yunovo_ffmpeg_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject instance,jstring str) {



#ifdef Debug
    LOGE("我在 native-lib.cpp里面和你聊呢！");
# endif

}



JNIEXPORT jint
JNICALL
Java_com_yunovo_ffmpeg_MainActivity_ffmpegRun(JNIEnv *env, jobject instance, jobjectArray commands) {
    int argc = (*env)->GetArrayLength(env,commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env,commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env,js, 0);
    }
    return jxRun(argc,argv);
}
