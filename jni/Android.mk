LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := JsonToGson
LOCAL_SRC_FILES := JsonToGson.cpp

include $(BUILD_SHARED_LIBRARY)
