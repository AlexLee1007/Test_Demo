package com.rick.testdemo.logic.test;

import com.rick.testdemo.entity.BaseEntity;

/**
 * package: TestContract
 * author: Rick
 * date: 2020-04-03 11:19
 * desc: V - P - M 三层之间的契约类
 */

public interface TestContract {


    interface Model {

        void executeMsgQuery(String pageNo);

    }

    interface View<T extends BaseEntity> {

        void handlerMsgResult(String str);

    }

    interface Presenter<T extends BaseEntity> {

        void requestMsg(String pageNo);


        void responseMsgResult(String str);
    }


}
