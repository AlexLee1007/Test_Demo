package com.rick.testdemo.retrofit;

import com.rick.testdemo.BuildConfig;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
                .connectTimeout(1000 * 90 * 1, TimeUnit.MILLISECONDS)
                .readTimeout(1000 * 90 * 1, TimeUnit.MILLISECONDS)
                .writeTimeout(1000 * 90 * 1, TimeUnit.MILLISECONDS);
        builder.addInterceptor(chain -> {

            Request original = chain.request();

//            String timeStamp = "";
//            String sign = "";
//            String signBase = "";

//            //获取
//            long currentTime = System.currentTimeMillis();
//            SimpleDateFormat sdf = new SimpleDateFormat("YYMMDDHHMMSS");
//            timeStamp = sdf.format(new Date(currentTime));
//
//            //获取请求中的业务参数
//            FormBody formBody = (FormBody) original.body();
//            JSONObject js = new JSONObject();
//            for (int i = 0; i < formBody.size(); i++) {
//                try {
//                    js.put(formBody.encodedName(i), formBody.encodedValue(i));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //添加POST 头部参数
//            signBase = Base64.getEncoder().encodeToString(js.toString().getBytes("UTF-8"));
//            Log.i("TAG", js.toString() + ":" + signBase);
//
//            sign = EncryptUtils.encryptHmacMD5ToString(signBase, timeStamp + ".west");
//
            Request.Builder builde = original.newBuilder();
//                    .addHeader("timeStamp", timeStamp)
//                    .addHeader("sign", sign);

            return chain.proceed(builde.build());
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
                .baseUrl("http://sxxby.xicp.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }


    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }


}
