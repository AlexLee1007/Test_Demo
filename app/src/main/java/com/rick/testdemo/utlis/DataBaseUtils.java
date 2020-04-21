package com.rick.testdemo.utlis;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.orhanobut.logger.Logger;
import com.rick.testdemo.room.database.AppDatabase;

public class DataBaseUtils {

    public static DataBaseUtils dataBaseUtils;
    private AppDatabase appDatabase;


    public static DataBaseUtils getInstance() {
        if (dataBaseUtils == null) {
            synchronized (DataBaseUtils.class) {
                if (dataBaseUtils == null) {
                    dataBaseUtils = new DataBaseUtils();
                }
            }
        }
        return dataBaseUtils;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void init(Context mContext) {
        appDatabase = Room
                .databaseBuilder(mContext.getApplicationContext(), AppDatabase.class, "room_dev.db")
//                .addCallback(new RoomDatabase.Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        Logger.i("onCreate===========" + db.getVersion() + "===" + db.getPath());
//                    }
//
//                    @Override
//                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                        super.onOpen(db);
//                        Logger.i("onOpen===========" + db.getVersion() + "===" + db.getPath());
//                    }
//                })
                .allowMainThreadQueries() //允许在主线程查询数据
                //.addMigrations(migration) //迁移数据库使用，下面会单独拿出来讲
                //.fallbackToDestructiveMigration() //迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                .build();
    }


}
