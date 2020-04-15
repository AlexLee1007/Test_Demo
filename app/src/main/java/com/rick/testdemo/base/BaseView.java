package com.rick.testdemo.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * package: BaseView
 * author: Rick
 * date: 2020-04-03 09:06
 * desc:
 */


public abstract class BaseView<P extends BasePresenter, CONTRACT> extends Activity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取Presenter
        mPresenter = getPresenter();

        if (mPresenter != null) {
            //绑定
            mPresenter.bindView(this);
        }

    }

    public abstract CONTRACT getContract();


    public abstract P getPresenter();


    public void error(Exception e) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁时解除绑定
        if(mPresenter != null) {
            mPresenter.unBindView();
        }

    }
}
