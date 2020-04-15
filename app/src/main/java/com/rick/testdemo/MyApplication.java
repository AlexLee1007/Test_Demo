package com.rick.testdemo;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.rick.testdemo.retrofit.RetrofitUtility;

/**
 * package: MyApplication
 * author: Rick
 * date: 2020-04-03 11:10
 * desc:
 */


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUtility.getInstance().init();
    }
}
