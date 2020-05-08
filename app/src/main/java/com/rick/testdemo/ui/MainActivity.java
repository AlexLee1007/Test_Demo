package com.rick.testdemo.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.orhanobut.logger.Logger;
import com.rick.testdemo.R;
import com.rick.testdemo.base.ActivityManager;
import com.rick.testdemo.base.BaseView;
import com.rick.testdemo.entity.RoomEntity;
import com.rick.testdemo.logic.test.TestContract;
import com.rick.testdemo.logic.test.TestPresenter;
import com.rick.testdemo.network.RetrofitUtility;
import com.rick.testdemo.room.entity.User;
import com.rick.testdemo.utlis.xxpermissions.OnPermission;
import com.rick.testdemo.utlis.xxpermissions.Permission;
import com.rick.testdemo.utlis.xxpermissions.XXPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseView<TestPresenter, TestContract.View> implements View.OnClickListener {


    @BindView(R.id.main_btn)
    Button mainBtn;
    @BindView(R.id.main_tv)
    TextView mainTv;
    @BindView(R.id.main_openCamera)
    Button mainOpenCamera;
    @BindView(R.id.main_addUser)
    Button mainAddUser;
    @BindView(R.id.main_delUser)
    Button mainDelUser;
    @BindView(R.id.main_toBlueTootch)
    Button mainToBlueTootch;
    @BindView(R.id.main_baiduRTC)
    Button mainBaiduRTC;

    @Override
    protected void initCreate() {
        initPermissions();
        mainBaiduRTC.setOnClickListener(this::onClick);
        new Thread(new Runnable() {
            @Override
            public void run() {
            //    RetrofitUtility.getInstance().createWebSocket();
            }
        }).start();

    }

    @Override
    protected int getContextView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        User user = new User();
        switch (v.getId()) {
            case R.id.main_btn:
                //测试网络请求
                mPresenter.getContract().requestMsg("1");
                //测试MMKV持久化储存
//                MMKVUtils.getInstance().setEncode("key", "hhhhhh");
//                Logger.i(MMKVUtils.getInstance().getDecodeString("key"));
                //测试报错日志收集
                //tvDebugTest.setText("sssssssss");
                break;
            case R.id.main_openCamera:
                openCamera();
                break;
            case R.id.main_addUser:
                Logger.i(DebugDB.getAddressLog());
                user.firstName = "hhhhhss";
                user.lastName = "eeeeee";
//                DataBaseUtils.getInstance()
//                        .getAppDatabase()
//                        .userDao()
//                        .insertUsers(user);

                mPresenter.getContract().db_requestQueryUserMsg();
                break;
            case R.id.main_delUser:
                user.id = 126;
                user.firstName = "hhhhh";
                user.lastName = "eeeeee";
                mPresenter.getContract().db_requestDelMsg(user);
                break;
            case R.id.main_toBlueTootch:
                Intent intent = new Intent(this, BluetoothActivity.class);
                startActivity(intent);
                break;
            case R.id.main_baiduRTC:
                openBaiduRTC();
                break;
        }
    }

    public void openBaiduRTC() {
        XXPermissions.with(this).constantRequest().permission(
                Permission.RECORD_AUDIO,
                Permission.CAMERA,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.ACCESS_WIFI_STATE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        //权限申请成功
                        Intent intent = new Intent(MainActivity.this, BaiduRtcActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        //权限申请失败
                        Toast.makeText(MainActivity.this, "权限开启失败!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void initPermissions() {
//        XXPermissions.with(this).constantRequest().permission(
//                Permission.READ_EXTERNAL_STORAGE,
//                Permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                .request(new OnPermission() {
//                    @Override
//                    public void hasPermission(List<String> granted, boolean all) {
//                        //权限申请成功
//                    }
//
//                    @Override
//                    public void noPermission(List<String> denied, boolean quick) {
//                        //权限申请失败
//                    }
//                });
    }


    /**
     * 开启相机
     */
    private void openCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限申请");
        builder.setMessage("在设置中开启内存读写权限，以正常使用应用功能");
        builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                XXPermissions.gotoPermissionSettings(MainActivity.this);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        XXPermissions.with(this).permission(Permission.CAMERA).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean all) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1111111);
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                builder.show();
            }
        });
    }


    @Override
    public TestContract.View getContract() {
        return new TestContract.View() {
            //网络查询信息
            @Override
            public void handlerMsgResult(String str) {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
                LiveEventBus.get("requestStatus").post(str);
            }

            //删除数据库指定内容
            @Override
            public void db_handlerDelResult(int index) {
                if (index > 0) {
                    Toast.makeText(MainActivity.this, "删除成功!" + index, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "未找到当前删除数据!" + index, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void db_handlerQueryUserMsg(RoomEntity room) {
                for (User user : room.getUesrArray()) {
                    Logger.i("main: " + user.firstName + "," + user.lastName);
                }
                Logger.i("main: " + room.getUesrArray().size());
            }

        };
    }

    @Override
    public void LdBusListener(String s) {
        super.LdBusListener(s);
        Logger.i(this.getClass().getName() + s);
    }

    @Override
    public TestPresenter getPresenter() {
        return new TestPresenter();
    }


    @Override
    public void error(Exception e) {
    }


}
