package com.rick.testdemo.ui;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.rick.testdemo.R;
import com.rick.testdemo.base.ActivityManager;
import com.rick.testdemo.base.BaseView;
import com.rick.testdemo.entity.BlueToothEntity;
import com.rick.testdemo.logic.btlogic.BluetoothContract;
import com.rick.testdemo.logic.btlogic.BluetoothPresenter;
import com.rick.testdemo.ui.adapter.AvAdapter;
import com.rick.testdemo.ui.adapter.PairAdapter;
import com.rick.testdemo.utlis.BlueToothUtils;

import java.util.List;


public class BluetoothActivity extends BaseView<BluetoothPresenter, BluetoothContract.View> {

    private Button bt_Open;
    private Button bt_Close;
    private LinearLayout bt_beviceTable;
    private ViewStub bt_pairListview;
    private ViewStub bt_availableListview;
    private TextView bt_pairTv;
    private TextView bt_availableTv;
    private RecyclerView bt_pairRcView;
    private RecyclerView bt_avRcView;

    private PairAdapter mPa;
    private AvAdapter avAdapter;

    private List<BlueToothEntity> btnArray;
    private List<BlueToothEntity> availableArray;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Activity activity = ActivityManager.getInstance().getActivity(BluetoothActivity.class);
            super.handleMessage(msg);
            if (activity != null) {

            }
        }
    };


    @Override
    protected void initCreate() {
        initView();
        initListener();
    }

    @Override
    protected int getContextView() {
        return R.layout.activity_bluetooth;
    }

    private void initView() {
        bt_Close = this.findViewById(R.id.bt_Close);
        bt_Open = this.findViewById(R.id.bt_Open);
        bt_beviceTable = this.findViewById(R.id.bt_beviceTable);
        bt_pairListview = this.findViewById(R.id.bt_pairListview);
        bt_availableListview = this.findViewById(R.id.bt_availableListview);
        //初始化调用一次再次调用报错
        View pairView = bt_pairListview.inflate();
        View availView = bt_availableListview.inflate();
        bt_pairTv = pairView.findViewById(R.id.bt_tvTitle);
        bt_availableTv = availView.findViewById(R.id.bt_tvTitle);
        bt_pairRcView = pairView.findViewById(R.id.bt_reacyclerView);
        bt_avRcView = availView.findViewById(R.id.bt_reacyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bt_pairTv.setText("已配对的设备");
        bt_availableTv.setText("可用设备");
        //初始化列表显示隐藏
        if (!BlueToothUtils.getInstance().isBTEbabled()) {
            //  bt_beviceTable.setVisibility(View.GONE);
        } else {
            //初始化蓝牙开启
            BlueToothUtils.getInstance().initializeStart(this);
            BlueToothUtils.getInstance().setmHandler(mHandler);
        }
        btnArray = BlueToothUtils.getInstance().getPairArray();
        availableArray = BlueToothUtils.getInstance().getAvailableArray();
        //绑定已配对设备
        LinearLayoutManager pairllm = new LinearLayoutManager(this);
        pairllm.setOrientation(RecyclerView.VERTICAL);
        mPa = new PairAdapter(this, btnArray);
        bt_pairRcView.setLayoutManager(pairllm);
        bt_pairRcView.setAdapter(mPa);

        //绑定可用设备
        LinearLayoutManager avllm = new LinearLayoutManager(this);
        avllm.setOrientation(RecyclerView.VERTICAL);
        avAdapter = new AvAdapter(this, availableArray);
        bt_avRcView.setLayoutManager(avllm);
        bt_avRcView.setAdapter(avAdapter);

        //注册消息总线
        LiveEventBus.get("blueToothAvailable", String.class).observeSticky(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                availableArray = BlueToothUtils.getInstance().getAvailableArray();
                avAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initListener() {
        bt_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlueToothUtils.getInstance().openBlueTooth(BluetoothActivity.this, new BlueToothUtils.btOpenListener() {
                    @Override
                    public void openSuccess(List<BlueToothEntity> pairArray, List<BlueToothEntity> availableArrays) {
                        btnArray = pairArray;
                        availableArray = availableArrays;
                        mPa.notifyDataSetChanged();
                        avAdapter.notifyDataSetChanged();
                        //  bt_beviceTable.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        bt_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlueToothUtils.getInstance().closeBlueTooth(BluetoothActivity.this, new BlueToothUtils.btCloseListener() {
                    @Override
                    public void closeSuccess(List<BlueToothEntity> pairArray, List<BlueToothEntity> availableArrays) {
                        btnArray = pairArray;
                        availableArray = availableArrays;
                        mPa.notifyDataSetChanged();
                        avAdapter.notifyDataSetChanged();
                    }
                });
                // bt_beviceTable.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        BlueToothUtils.getInstance().initializeStop(this);
    }

    @Override
    public BluetoothContract.View getContract() {
        return new BluetoothContract.View() {
        };
    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public BluetoothPresenter getPresenter() {
        return new BluetoothPresenter();
    }

}
