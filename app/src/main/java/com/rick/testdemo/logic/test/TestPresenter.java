package com.rick.testdemo.logic.test;

import com.rick.testdemo.base.BasePresenter;
import com.rick.testdemo.entity.RoomEntity;
import com.rick.testdemo.room.entity.User;
import com.rick.testdemo.ui.MainActivity;

import java.util.List;

/**
 * package: TestPresenter
 * author: Rick
 * date: 2020-04-03 11:13
 * desc:
 */


public class TestPresenter extends BasePresenter<MainActivity, TestModel, TestContract.Presenter> {

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

            @Override
            public void db_requestDelMsg(User user) {
                mModel.getContract().db_executeDelMsg(user);
            }

            @Override
            public void db_responseDelMsgResult(int index) {
                getView().getContract().db_handlerDelResult(index);
            }

            @Override
            public void db_requestQueryUserMsg() {
                mModel.getContract().db_exectueQueryUserMsg();
            }

            @Override
            public void db_responseQueryUserMsgResult(RoomEntity room) {
                getView().getContract().db_handlerQueryUserMsg(room);
            }
        };
    }

    @Override
    public TestModel getModel() {
        return new TestModel(this);
    }

}