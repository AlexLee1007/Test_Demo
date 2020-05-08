package com.rick.testdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.baidu.rtc.BaiduRtcRoom;
import com.rick.testdemo.R;
import com.rick.testdemo.utlis.BaiduRTCUtils;

public class BaiduRtcActivity extends Activity {

    private BaiduRTCUtils bru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_rtc);

        bru = BaiduRTCUtils.getInstance();
        bru.initCreateChat(getApplicationContext());
        bru.singleChat(findViewById(R.id.local_rtc_video_view), findViewById(R.id.remote_rtc_video_view));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bru.OutChat();
    }

    public void openChat(View view) {

        String roomName = "aaa";
        long mUserId = 4545454;
        String mUsername = "Rick_s";
        bru.loginRtcChatRoom(roomName, mUserId, mUsername);

    }


}
