package com.rick.testdemo.entity;

import com.rick.testdemo.room.entity.User;

import java.util.List;

/**
 * package: RoomEntity
 * author: Rick Li
 * date: 2020/4/21 9:27
 * desc: 数据库查询类
 */
public class RoomEntity {

    private List<User> uesrArray;


    public List<User> getUesrArray() {
        return uesrArray;
    }

    public void setUesrArray(List<User> uesrArray) {
        this.uesrArray = uesrArray;
    }

}
