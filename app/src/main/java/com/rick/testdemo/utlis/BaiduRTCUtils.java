package com.rick.testdemo.utlis;

import android.content.Context;

import com.baidu.rtc.BaiduRtcRoom;
import com.baidu.rtc.RTCAudioSamples;
import com.baidu.rtc.RTCVideoView;
import com.baidu.rtc.RtcParameterSettings;

import java.nio.ByteBuffer;

/**
 * package: BaiduRTCUtils
 * author: Rick Li
 * date: 2020/5/7 14:27
 * desc:
 */
public class BaiduRTCUtils implements BaiduRtcRoom.BaiduRtcRoomDelegate {

    private static BaiduRTCUtils mRtc;
    private BaiduRtcRoom mRtcRoom;

    public static BaiduRTCUtils getInstance() {
        if (mRtc == null) {
            synchronized (BaiduRTCUtils.class) {
                if (mRtc == null) {
                    mRtc = new BaiduRTCUtils();
                }
            }
        }
        return mRtc;
    }

    public void initCreateChat(Context mContext) {
        String version = "004";
        String appKey = "ak7dvebthcr0";
        TokenUtil tokenUtil = new TokenUtil();
        String token = tokenUtil.constructToken(version, "appkder0c7v9qes", appKey, "aaa", "4545454", 0);
        mRtcRoom = BaiduRtcRoom.initWithAppID(mContext, "appkder0c7v9qes", token);
        mRtcRoom.setBaiduRtcRoomDelegate(this);

        RtcParameterSettings cfg = RtcParameterSettings.getDefaultSettings();

    }


    public void singleChat(RTCVideoView local, RTCVideoView remote) {
        mRtcRoom.setLocalDisplay(local);
        mRtcRoom.setRemoteDisplay(remote);
    }

    public void GroupChat(RTCVideoView local, RTCVideoView... remote) {
        mRtcRoom.setLocalDisplay(local);
        mRtcRoom.setRemoteDisplayGroup(remote);
    }

    public void loginRtcChatRoom(String roomName, long mUserId, String mUsername) {
        mRtcRoom.loginRtcRoomWithRoomName(roomName, mUserId, mUsername);

        mRtcRoom.setAuidoSamplesReadyCallback(new RTCAudioSamples.RTCSamplesReadyCallback() {
            @Override
            public void onRtcAudioRecordSamplesReady(RTCAudioSamples rtcAudioSamples) {

            }
        });
    }

    public void OutChat() {
        mRtcRoom.logoutRtcRoom();
        mRtcRoom.destroy();
    }


    @Override
    public void onRoomEventUpdate(int i, long l, String s) {

    }

    @Override
    public void onPeerConnectStateUpdate(int i) {

    }

    @Override
    public void onStreamInfoUpdate(String[] strings) {

    }

    @Override
    public void onErrorInfoUpdate(int i) {

    }

    @Override
    public void onEngineStatisticsInfo(int i) {

    }

    @Override
    public void onRoomDataMessage(ByteBuffer byteBuffer) {

    }

}
