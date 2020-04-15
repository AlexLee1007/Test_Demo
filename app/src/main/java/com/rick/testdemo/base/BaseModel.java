package com.rick.testdemo.base;

/**
 * package: BaseModel
 * author: Rick
 * date: 2020-04-03 09:02
 * desc:
 */


public abstract class BaseModel <P extends  BasePresenter,CONTRACT> {

    protected P mPresenter;

    public BaseModel(P presenter) {
        this.mPresenter = presenter;
    }

    public abstract CONTRACT getContract();

}
