package com.rick.testdemo.room.entity;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * tableName：设置表名字。默认是类的名字。
 * indices：设置索引。
 * inheritSuperIndices：父类的索引是否会自动被当前类继承。
 * primaryKeys：设置主键。
 * foreignKeys：设置外键
 */
@Entity(tableName = "users")
public class User {

    //主键 并设置自增列
    @PrimaryKey(autoGenerate = true)
    public int id;

    //默认使用字段名
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;

    //不映射到表中
    @Ignore
    Bitmap picture;

}
