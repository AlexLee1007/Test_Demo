package com.rick.testdemo.logic.btlogic;

import com.rick.testdemo.base.BasePresenter;
import com.rick.testdemo.entity.RoomEntity;
import com.rick.testdemo.room.entity.User;
import com.rick.testdemo.ui.MainActivity;

/**
 * package: TestPresenter
 * author: Rick
 * date: 2020-04-03 11:13
 * desc:
 */


public class BluetoothPresenter extends BasePresenter<MainActivity, BluetoothModel, BluetoothContract.Presenter> {

    @Override
    public BluetoothContract.Presenter getContract() {
        return new BluetoothContract.Presenter() {

        };
    }

    @Override
    public BluetoothModel getModel() {
        return new BluetoothModel(this);
    }

}