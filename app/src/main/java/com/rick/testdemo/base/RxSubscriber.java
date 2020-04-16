package com.rick.testdemo.base;

import android.widget.Toast;

import com.rick.testdemo.entity.BaseEntity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 封装RxJava观察者处理请求公共逻辑
 *
 * @param <T>
 */
public abstract class RxSubscriber<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        //自定义加载框启动
    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseEntity) {
            BaseEntity be = (BaseEntity) t;
            this.rx_next((T) be, "");
        } else {

        }
    }

    @Override
    public void onError(Throwable e) {
        rx_Error();
    }

    @Override
    public void onComplete() {

    }

    public abstract void rx_next(T t, String msg);

    public abstract void rx_Error();


}
