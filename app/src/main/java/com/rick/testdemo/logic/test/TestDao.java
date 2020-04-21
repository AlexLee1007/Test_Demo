package com.rick.testdemo.logic.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.rick.testdemo.room.entity.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface TestDao {

    /**
     * onConflict：默认值是OnConflictStrategy.ABORT，表示当插入有冲突的时候的处理策略。
     * OnConflictStrategy 封装了Room解决冲突的相关策略:
     * 1. OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。
     * 2. OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。
     * 3. OnConflictStrategy.ABORT：冲突策略是终止事务。
     * 4. OnConflictStrategy.FAIL：冲突策略是事务失败。
     * 5. OnConflictStrategy.IGNORE：冲突策略是忽略冲突。
     *
     * @param users
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUsers(User... users);

    @Delete
    Single<Integer> deleteUsers(User... users);

    @Query("SELECT * FROM users")
    Observable<List<User>> selAllUser();

    @Query("SELECT * FROM users WHERE first_name == :name")
    Observable<List<User>> selAllUserByFirstName(String name);

}
