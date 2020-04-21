package com.rick.testdemo.logic.test;

import androidx.room.Room;

import com.rick.testdemo.entity.BaseEntity;
import com.rick.testdemo.entity.RoomEntity;
import com.rick.testdemo.room.entity.User;

import java.util.List;

/**
 * package: TestContract
 * author: Rick
 * date: 2020-04-03 11:19
 * desc: V - P - M 三层之间的契约类
 */

public interface TestContract {


    interface Model {

        void executeMsgQuery(String pageNo);

        void db_executeDelMsg(User user);

        void db_exectueQueryUserMsg();
    }

    interface View<T extends BaseEntity,  R extends RoomEntity> {

        void handlerMsgResult(String str);

        void db_handlerDelResult(int index);

        void db_handlerQueryUserMsg(R room);
    }

    interface Presenter<T extends BaseEntity, R extends RoomEntity> {

        void requestMsg(String pageNo);

        void responseMsgResult(String str);

        void db_requestDelMsg(User user);

        void db_responseDelMsgResult(int index);

        void db_requestQueryUserMsg();

        void db_responseQueryUserMsgResult(R room);
    }


}
