package com.rick.testdemo.retrofit;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.rick.testdemo.BuildConfig;
import com.rick.testdemo.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * package: RetrofitUtility
 * author: Rick
 * date: 2020-04-03 09:13
 * desc:
 */


public class RetrofitUtility {

    private static RetrofitUtility mRetrofitUtility;

    private Retrofit mRetrofit;

    private OkHttpClient mClient;

    private OkHttpClient.Builder builder;


    public static RetrofitUtility getInstance() {
        if (mRetrofitUtility == null) {
            synchronized (RetrofitUtility.class) {
                if (mRetrofitUtility == null) {
                    mRetrofitUtility = new RetrofitUtility();
                }
            }
        }
        return mRetrofitUtility;
    }


    public void init() {
        if (mClient == null) {
            createOkhttpClinet();
        }
        createRetrofit(mClient);
    }


    public void createOkhttpClinet() {
        builder = new OkHttpClient.Builder()
                .connectTimeout(Constant.REQUEST_NETWORK_TIME, TimeUnit.MILLISECONDS)
                .readTimeout(Constant.REQUEST_NETWORK_TIME, TimeUnit.MILLISECONDS)
                .writeTimeout(Constant.REQUEST_NETWORK_TIME, TimeUnit.MILLISECONDS);
        builder.addInterceptor(chain -> {
            Request original = chain.request();
            if (BuildConfig.DEBUG) {
                JSONObject json = new JSONObject();
                Long startTime = System.currentTimeMillis(); //开始时间
                Response response = chain.proceed(chain.request());
                Long endTime = System.currentTimeMillis(); //结束时间
                long duration = endTime - startTime;
                //获取请求参数
                FormBody formBody = (FormBody) original.body();
                try {
                    for (int i = 0; i < formBody.size(); i++) {
                        json.put(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Logger.i(String.format("请求地址=========> %s\n请求方式=========> %s", original.url(), original.method()));
                Log.i(Constant.TAG, "====================> 请求参数");
                Logger.json(json.toString());
                Log.i(Constant.TAG, "====================> 响应参数");
                Logger.json(response.body().string());
                Logger.i("=========> 请求耗时:" + duration + "毫秒 <=========");
                json = null; //用完回收对象 程序计数器置空
            }
            return chain.proceed(original.newBuilder().build());
        });


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        mClient = builder.build();
    }


    public Retrofit createRetrofit(OkHttpClient client) {
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constant.HTTP_REQUEST_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;

    }


    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }


}
