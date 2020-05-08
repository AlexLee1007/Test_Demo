package com.rick.testdemo.utlis;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.Toast;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.rick.testdemo.entity.BlueToothEntity;
import com.rick.testdemo.utlis.xxpermissions.OnPermission;
import com.rick.testdemo.utlis.xxpermissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * package: BlueToothUtils
 * author: Rick Li
 * date: 2020/4/21 16:38
 * desc:
 */
public class BlueToothUtils {

    private static BlueToothUtils mBlueToothUtils;

    private static Context mContext;

    // public static int REQUEST_ENBLE_BT = 101010;
//    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//    ac.startActivityForResult(intent,REQUEST_ENBLE_BT);

    /**
     * 蓝牙适配器
     */
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    /**
     * 已配对所有设备的集合
     */
    private Set<BluetoothDevice> pairedDevices;

    /**
     * 已配对所有设备信息集合
     */
    private List<BlueToothEntity> pairArray = new ArrayList<>();

    /**
     * 附件可扫描到的蓝牙设备
     */
    private List<BlueToothEntity> availableArray = new ArrayList<>();

    private Handler mHandler;


    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    /**
     * 广播接收者 接收附近的设备信息
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BlueToothEntity bte = new BlueToothEntity();
                bte.setBtName(device.getName());
                bte.setBtAddress(device.getAddress());
                Logger.i("收到广播：" + bte.toString());
                if (availableArray.size() > 0) {
                    for (int i = 0; i < availableArray.size(); i++) {
                        if (availableArray.get(i).getBtAddress().equals(bte.getBtAddress())) {
                            availableArray.remove(i);
                        }
                    }
                }
                availableArray.add(bte);
                LiveEventBus.get("blueToothAvailable").post("");
            }
        }
    };


    public static BlueToothUtils getInstance() {
        if (mBlueToothUtils == null) {
            synchronized (BlueToothUtils.class) {
                if (mBlueToothUtils == null) {
                    mBlueToothUtils = new BlueToothUtils();
                }
            }
        }
        return mBlueToothUtils;
    }

    public void init(Context mContex) {
        this.mContext = mContex;
    }

    public boolean isBTEbabled() {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.isEnabled();
        }
        return false;
    }

    /**
     * 页面启动时初始页面
     */
    public void initializeStart(Activity ac) {
        openBlueTooth(ac, null);
    }

    /**
     * 页面停止时初始页面
     */
    public void initializeStop(Activity ac) {
        closeBlueTooth(ac, null);
    }

    public void openBlueTooth(Activity ac, btOpenListener bo) {
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "当前设备不支持蓝牙功能!", Toast.LENGTH_SHORT).show();
            return;
        }
        XXPermissions.with(ac).permission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean all) {
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();//静默蓝牙开启方式
                    Logger.i(mBluetoothAdapter.isEnabled() + "==========================");
                    //    isBTStatus(null, bo, "ON");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                Toast.makeText(ac, "功能需开启权限!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void closeBlueTooth(Activity ac, btCloseListener bc) {
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "当前设备不支持蓝牙功能!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (XXPermissions.isHasPermission(ac.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
                isBTStatus(bc, null, "OFF");
            }
        }
    }

    private void isBTStatus(btCloseListener bc, btOpenListener bo, String status) {
        boolean bool = true;
        do {
            switch (mBluetoothAdapter.getState()) {
                case BluetoothAdapter.STATE_ON:
                    //蓝牙已打开
                    if (bo != null) {
                        clearAllArray();
                        registerBtReceiver();
                        bo.openSuccess(getPairArray(), getAvailableArray());
                        Logger.i("bo not null");
                    }
                    Logger.i("蓝牙已打开");
                    if (status.equals("ON")) {
                        bool = false;
                    }
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    //蓝牙正在打开
                    Logger.i("蓝牙正在打开");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    //蓝牙正在关闭
                    Logger.i("蓝牙正在关闭");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    //蓝牙已关闭
                    if (bc != null) {
                        clearAllArray();//蓝牙关闭清空集合
                        unregisterBtReceiver();
                        bc.closeSuccess(getPairArray(), getAvailableArray());
                        Logger.i("bc not null");
                    }
                    Logger.i("蓝牙已关闭");
                    if (status.equals("OFF")) {
                        bool = false;
                    }
                    break;
            }
            Logger.i(bool + "<===============>");
        } while (bool);
    }

    /**
     * 清空集合内容
     */
    private void clearAllArray() {
        if (pairArray != null && pairArray.size() > 0) {
            pairArray.clear();
        }
        if (availableArray != null && availableArray.size() > 0) {
            availableArray.clear();
        }
    }


    public List<BlueToothEntity> getPairArray() {
        if (mBluetoothAdapter != null) {
            pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice pairedDevice : pairedDevices) {
                    BlueToothEntity bte = new BlueToothEntity();
                    bte.setBtName(pairedDevice.getName());
                    bte.setBtAddress(pairedDevice.getAddress());
                    pairArray.add(bte);
                }
            }
        }
        return pairArray;
    }

    public List<BlueToothEntity> getAvailableArray() {
        return availableArray;
    }


    private void registerBtReceiver() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, filter);
        mBluetoothAdapter.startDiscovery();
        Logger.i("注册成功");
    }

    private void unregisterBtReceiver() {
        mBluetoothAdapter.cancelDiscovery();
        mContext.unregisterReceiver(mReceiver);
        Logger.i("注册取消");
    }


    public interface btCloseListener {
        void closeSuccess(List<BlueToothEntity> pairArray, List<BlueToothEntity> availableArray);
    }

    public interface btOpenListener {
        void openSuccess(List<BlueToothEntity> pairArray, List<BlueToothEntity> availableArray);
    }

}


