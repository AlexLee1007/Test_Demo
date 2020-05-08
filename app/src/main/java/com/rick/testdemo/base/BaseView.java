package com.rick.testdemo.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;

import butterknife.ButterKnife;

/**
 * package: BaseView
 * author: Rick
 * date: 2020-04-03 09:06
 * desc:
 */


public abstract class BaseView<P extends BasePresenter, CONTRACT> extends AppCompatActivity {

    protected P mPresenter;

    //  private LifecycleRegistry mLifecycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContextView());
        //添加Activity工程管理
        ActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        //获取Presenter
        mPresenter = getPresenter();
        if (mPresenter != null) {
            //绑定
            mPresenter.bindView(this);
        }
        initCreate();
        //(BaseView继承于Activity需要实现LifecycleOwner 当前框架暂时不需要生命感知只需配合LiveEventBus做粘性事件使用)
        //  mLifecycle = new LifecycleRegistry(this);
        // 绑定 LiveDataBus 消息总线 (基类中可以绑定共有的长连接，但是单独的界面需要刷新数据单独去子类绑定)
        LiveEventBus.get("requestStatus", String.class).observeSticky(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LdBusListener(s);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁时解除绑定
        if (mPresenter != null) {
            mPresenter.unBindView();
        }
//        if (mLifecycle != null) {
//            mLifecycle = null;
//        }
        ActivityManager.getInstance().removeActivity(this);
    }

    // 废弃集成Activity时需要
//    @NonNull
//    @Override
//    public Lifecycle getLifecycle() {
//        return mLifecycle;
//    }

    protected abstract void initCreate();

    protected abstract int getContextView();

    public abstract P getPresenter();

    public abstract CONTRACT getContract();

    public abstract void error(Exception e);

    public void LdBusListener(String s) {
    }

}
