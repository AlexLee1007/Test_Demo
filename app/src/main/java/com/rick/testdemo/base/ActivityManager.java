package com.rick.testdemo.base;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Activity管理
 */
public class ActivityManager {

    private static ActivityManager instance;

    //Activity弱引用栈
    private static LinkedList<WeakReference<Activity>> activityStack;

    private ActivityManager() {

    }

    public static LinkedList<WeakReference<Activity>> getActivityStack() {
        return activityStack;
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈顶
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<>();
        }
        activityStack.addFirst(new WeakReference<>(activity));
    }

    /**
     * 获取栈顶Activity
     * @return
     */
    public Activity getTopActivity() {
        checkWeakReference();
        if (activityStack != null && !activityStack.isEmpty()) {
            return activityStack.getFirst().get();
        }
        return null;
    }

    /**
     * 移除指定Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if (activity != null) {
            activityStack.remove(new WeakReference<>(activity));
        }
    }

    /**
     * 结束栈顶Activity
     */
    public void finishTopActivity() {
        Activity activity = activityStack.getFirst().get();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 结束指定Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 结束指定Activity
     * @param type
     */
    public void finishActivity(Class<?> type) {
        Activity activity = getActivity(type);
        finishActivity(activity);
    }

    /**
     * 结束指定Activity之外的所有Activity
     * @param activity
     */
    public void finishOtherActivity(Activity activity) {
        if (activity != null) {
            for (Iterator<WeakReference<Activity>> iterator = activityStack.iterator() ; iterator.hasNext() ; ) {
                Activity ac = iterator.next().get();
                if (!ac.equals(activity) && !ac.isFinishing()) {
                    ac.finish();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 结束指定Activity之外的所有Activity
     * @param type
     */
    public void finishOtherActivity(Class<?> type) {
        Activity activity = getActivity(type);
        finishOtherActivity(activity);
    }

    /**
     * 结束栈内所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (WeakReference<Activity> reference : activityStack) {
                Activity activity = reference.get();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
            checkWeakReference();
        }
    }

    /**
     * 根据弱引用和Activity清理栈
     */
    private void checkWeakReference() {
        if (activityStack != null) {
            for (Iterator<WeakReference<Activity>> iterator = activityStack.iterator() ; iterator.hasNext() ; ) {
                Activity activity = iterator.next().get();
                if (activity == null || activity.isFinishing()) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 获取指定类名的Activity
     * @param type
     * @return
     */
    public Activity getActivity(Class<?> type) {
        checkWeakReference();
        for (WeakReference<Activity> reference : activityStack) {
            Activity activity = reference.get();
            if (activity.getClass().equals(type)) {
                return activity;
            }
        }
        return null;
    }
}
