package com.rick.testdemo.utlis.video;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * package: EmptyControlVidep
 * author: Rick Li
 * date: 2020/4/26 15:02
 * desc:
 */
public class EmptyControlVidep extends StandardGSYVideoPlayer {

    public EmptyControlVidep(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public EmptyControlVidep(Context context) {
        super(context);
    }

    public EmptyControlVidep(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false;
    }
}
