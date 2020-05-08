package com.rick.testdemo.logic.btlogic;

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


public class BluetoothModel extends BaseModel<BluetoothPresenter, BluetoothContract.Model> {


    public BluetoothModel(BluetoothPresenter presenter) {
        super(presenter);
    }

    @Override
    public BluetoothContract.Model getContract() {

        return new BluetoothContract.Model() {

        };

    }

}
