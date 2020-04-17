package com.rick.testdemo.ui;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.rick.testdemo.R;
import com.rick.testdemo.base.BaseView;
import com.rick.testdemo.logic.test.TestContract;
import com.rick.testdemo.logic.test.TestPresenter;
import com.rick.testdemo.utlis.MMKVUtils;
import com.rick.testdemo.utlis.xxpermissions.OnPermission;
import com.rick.testdemo.utlis.xxpermissions.Permission;
import com.rick.testdemo.utlis.xxpermissions.XXPermissions;

import java.util.List;

public class MainActivity extends BaseView<TestPresenter, TestContract.View> {

    private Button main_btn;
    private TextView main_tv;
    private Button main_openCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermissions();
        initView();
        initListener();
    }

    @Override
    public void LdBusListener(String s) {
        Logger.i(this.getClass().getName() + s);
    }


    private void initView() {
        main_btn = this.findViewById(R.id.main_btn);
        main_tv = this.findViewById(R.id.main_tv);
        main_openCamera = this.findViewById(R.id.main_openCamera);
    }

    private void initListener() {
        TextView tvDebugTest = null;
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试网络请求
                //mPresenter.getContract().requestMsg("1");
                //测试MMKV持久化储存
                MMKVUtils.getInstance().setEncode("key", "hhhhhh");
                Logger.i(MMKVUtils.getInstance().getDecodeString("key"));
                //测试报错日志收集
                tvDebugTest.setText("sssssssss");
            }
        });

        main_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   XXPermissions.with(MainActivity.this).permission();
                openCamera();
            }
        });
    }

    private void initPermissions() {
        XXPermissions.with(this).constantRequest().permission(Permission.Group.STORAGE).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean all) {

            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }

    /**
     * 开启相机
     */
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1111111);
    }


    @Override
    public TestContract.View getContract() {
        return new TestContract.View() {
            @Override
            public void handlerMsgResult(String str) {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                main_tv.setText(str);
                LiveEventBus.get("requestStatus").post(str);
            }
        };
    }

    @Override
    public TestPresenter getPresenter() {
        return new TestPresenter();
    }


    @Override
    public void error(Exception e) {
        Logger.e(e.getMessage());
    }

}
