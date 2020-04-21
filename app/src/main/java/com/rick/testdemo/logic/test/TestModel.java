package com.rick.testdemo.logic.test;

import com.orhanobut.logger.Logger;
import com.rick.testdemo.base.BaseModel;
import com.rick.testdemo.base.RxSchedulers;
import com.rick.testdemo.base.RxSubscriber;
import com.rick.testdemo.entity.BaseEntity;
import com.rick.testdemo.entity.RoomEntity;
import com.rick.testdemo.network.RetrofitUtility;
import com.rick.testdemo.room.entity.User;
import com.rick.testdemo.utlis.DataBaseUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
                        .compose(RxSchedulers.ioToMain())
                        .subscribe(new RxSubscriber<BaseEntity>() {
                            @Override
                            public void rx_next(BaseEntity baseEntity, String msg) {
                                mPresenter.getContract().responseMsgResult(baseEntity.toString());
                            }

                            @Override
                            public void rx_Error(Throwable e) {

                            }
                        });
            }

            @Override
            public void db_executeDelMsg(User user) {
                DataBaseUtils.getInstance()
                        .getAppDatabase()
                        .userDao()
                        .deleteUsers(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(Integer integer) {
                                mPresenter.getContract().db_responseDelMsgResult(integer);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }

            @Override
            public void db_exectueQueryUserMsg() {
                DataBaseUtils.getInstance()
                        .getAppDatabase()
                        .userDao()
                        .selAllUser()
                        .compose(RxSchedulers.ioToMain())
                        .subscribe(new RxSubscriber<List<User>>() {
                            @Override
                            public void rx_next(List<User> users, String msg) {
                                RoomEntity roomEntity = new RoomEntity();
                                roomEntity.setUesrArray(users);
                                mPresenter.getContract().db_responseQueryUserMsgResult(roomEntity);
                            }

                            @Override
                            public void rx_Error(Throwable e) {

                            }
                        });
            }


        };
    }

}
