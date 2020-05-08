package com.rick.testdemo.logic.btlogic;

import com.rick.testdemo.entity.BaseEntity;
import com.rick.testdemo.entity.RoomEntity;
import com.rick.testdemo.room.entity.User;

/**
 * package: TestContract
 * author: Rick
 * date: 2020-04-03 11:19
 * desc: V - P - M 三层之间的契约类
 */

public interface BluetoothContract {


    interface Model {

        //     void executeMsgQuery(String pageNo);

    }

    interface View<T extends BaseEntity, R extends RoomEntity> {

        //    void handlerMsgResult(String str);
    }

    interface Presenter<T extends BaseEntity, R extends RoomEntity> {

        //   void requestMsg(String pageNo);

        //   void responseMsgResult(String str);
    }


}
