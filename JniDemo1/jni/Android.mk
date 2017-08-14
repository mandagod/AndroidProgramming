LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := sum-jni
LOCAL_SRC_FILES := sum-jni.c

include $(BUILD_SHARED_LIBRARY)
