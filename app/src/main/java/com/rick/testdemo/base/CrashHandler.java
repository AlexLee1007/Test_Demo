package com.rick.testdemo.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.rick.testdemo.utlis.xxpermissions.Permission;
import com.rick.testdemo.utlis.xxpermissions.XXPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler mCrashHandler;
    private Thread.UncaughtExceptionHandler mDefaulthandler;
    private Context mContext;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    /**
     * 收集异常信息
     */
    private Map<String, String> paramsMap = new HashMap<>();


    public static CrashHandler getInstance() {
        if (mCrashHandler == null)
            synchronized (CrashHandler.class) {
                if (mCrashHandler == null) {
                    mCrashHandler = new CrashHandler();
                }
            }
        return mCrashHandler;
    }

    public void init(Context context) {
        mContext = context;
        mDefaulthandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!handleException(e) && mDefaulthandler != null) {
            mDefaulthandler.uncaughtException(t, e); //系统处理
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.e(e.getMessage(), ex);
            }
            System.exit(0);
        }

    }

    /**
     * 收集错误,发送到服务器
     *
     * @param e
     * @return
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        //收集设备信息
        collectDeviceInfo(mContext);
        //添加自定义信息
        addCustomInfo();
        //使用Toast提示用户异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "系统程序异常...", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //保存日志文件
        if (XXPermissions.isHasPermission(mContext, Permission.Group.STORAGE)) {
            saveCrashInfoFile(e);
        }
        return true;
    }

    private void addCustomInfo() {
    }

    private void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                paramsMap.put("versionName", versionName);
                paramsMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("an error occured when collect package info", e);
        }

        //获取所有系统信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                paramsMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Logger.e("an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 报错错误信息保存到手机本地
     *
     * @param e
     * @return
     */
    private String saveCrashInfoFile(Throwable e) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(String.format("%s = %s\n", key, value));
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = sdf.format(new Date());
            String fileName = String.format("crash-%s-%s.log", time, timestamp);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory() + "/testdemo/log/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                } else {
                    String[] content = dir.list();//获取文件夹下所以的文件
                    for (String name : content) {
                        File temp = new File(dir, name);
                        temp.delete();
                    }
                    //Logger.i("File Url:" + path + fileName);
                }
                // Logger.i("File Url:" + path + fileName);
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception ex) {
            // ex.printStackTrace();
            Logger.e("an error occured while writing file...", ex);
        }
        return null;
    }


}
