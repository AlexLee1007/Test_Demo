package com.rick.testdemo.test;

import com.rick.testdemo.MainActivity;
import com.rick.testdemo.base.BasePresenter;
import com.rick.testdemo.entity.BaseEntity;

/**
 * package: TestPresenter
 * author: Rick
 * date: 2020-04-03 11:13
 * desc:
 */


public class TestPresenter extends BasePresenter<MainActivity,TestModel,TestContract.Presenter> {

    @Override
    public TestContract.Presenter getContract() {
        return new TestContract.Presenter() {
            @Override
            public void requestMsg(String pageNo) {
                mModel.getContract().executeMsgQuery(pageNo);
            }

            @Override
            public void responseMsgResult(String str) {
                getView().getContract().handlerMsgResult(str);
            }
        };
    }

    @Override
    public TestModel getModel() {
        return new TestModel(this);
    }

}