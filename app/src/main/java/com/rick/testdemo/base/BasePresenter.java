package com.rick.testdemo.base;

import java.lang.ref.WeakReference;

/**
 * package: BasePresenter
 * author: Rick
 * date: 2020-04-03 09:02
 * desc:
 */


public abstract class BasePresenter<V extends BaseView, M extends BaseModel, CONTRACT> {


    protected M mModel;

    //绑定View层弱引用（程序内存不足时，可进行回收）
    private WeakReference<V> mVWeakReference;

    public BasePresenter() {
        this.mModel = getModel();
    }

    /**
     * 绑定视图View
     *
     * @param view
     */
    public void bindView(V view) {
        mVWeakReference = new WeakReference<>(view);
    }


    /**
     * 解除绑定
     */
    public void unBindView() {
        if (mVWeakReference != null) {
            mVWeakReference.clear();
            mVWeakReference = null;
            System.gc();
        }
    }

    //获取View
    public V getView() {
        if (mVWeakReference != null) {
            return mVWeakReference.get();
        }
        return null;
    }

    public abstract CONTRACT getContract();

    public abstract M getModel();

}
