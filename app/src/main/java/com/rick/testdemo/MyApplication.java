package com.rick.testdemo;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
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

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .tag(Constant.TAG)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
