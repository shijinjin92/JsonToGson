package com.test.jsontogson;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		ApiStoreSDK.init(this, "0742a0b1236b4aec12573bea8054fc29");
		super.onCreate();
	}

}
