package com.rick.testdemo.logic.test;

import com.rick.testdemo.base.BaseModel;
import com.rick.testdemo.base.RxSchedulers;
import com.rick.testdemo.base.RxSubscriber;
import com.rick.testdemo.entity.BaseEntity;
import com.rick.testdemo.retrofit.RetrofitUtility;

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
        };
    }

}
