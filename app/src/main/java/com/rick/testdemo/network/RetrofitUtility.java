package com.rick.testdemo.network;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.rick.testdemo.BuildConfig;
import com.rick.testdemo.Constant;
import com.rick.testdemo.utlis.MockWebSockets;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import okio.ByteString;
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
        builder = new OkHttpClient.Builder().pingInterval(40, TimeUnit.SECONDS)
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

    public void createWebSocket() {
        MockWebSockets mws = new MockWebSockets();
        String hostName = mws.mockWebServer.getHostName();
        int port = mws.mockWebServer.getPort();
        String url = String.format("ws:%s:%s", hostName, port);
        Request request = new Request.Builder().url(url).build();
        mClient.newWebSocket(request, new EchoWebSocketListener());
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


    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Logger.i("连接创建成功!");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Logger.i("收到新消息!");
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            Logger.i("收到新消息Bytes!");
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            Logger.i("服务端主动关闭!");
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            Logger.i("连接关闭!");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            Logger.i("出错了!");
        }
    }


}
