package com.rick.testdemo.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rick.testdemo.logic.test.TestDao;
import com.rick.testdemo.room.entity.User;


@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TestDao userDao();

}
