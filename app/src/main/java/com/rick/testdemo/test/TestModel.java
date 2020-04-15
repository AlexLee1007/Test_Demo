package com.rick.testdemo.test;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.rick.testdemo.base.BaseModel;
import com.rick.testdemo.entity.BaseEntity;
import com.rick.testdemo.retrofit.RetrofitUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * package: TestModel
 * author: Rick
 * date: 2020-04-03 11:14
 * desc:
 */


public class TestModel extends BaseModel<TestPresenter, TestContract.Model> {


    public TestModel(TestPresenter presenter) {
        super(presenter);
    }

    @Override
    public TestContract.Model getContract() {

        return new TestContract.Model() {
            @Override
            public void executeMsgQuery(String id) {
                RetrofitUtility.getInstance().create(TestApiService.class)
                        .queryMsgResult(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseEntity>() {
                            @Override
                            public void accept(BaseEntity baseEntity) throws Exception {
                                mPresenter.getContract().responseMsgResult(baseEntity.toString());
                            }
                        });
            }
        };

    }

}
