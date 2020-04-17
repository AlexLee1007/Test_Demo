package com.rick.testdemo.utlis;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

public class MMKVUtils {

    private static MMKVUtils sp;
    private MMKV mmkv = null;

    public static MMKVUtils getInstance() {
        if (sp == null) {
            synchronized (MMKVUtils.class) {
                sp = new MMKVUtils();
            }
        }
        return sp;
    }

    public MMKVUtils() {
        this.mmkv = MMKV.defaultMMKV();
    }

    public static void init(Context mContext) {
        MMKV.initialize(mContext);
    }

    public void setEncode(String key, Object value) {
        if (mmkv == null && value == null) {
            return;
        }
        if (value instanceof String) {
            mmkv.encode(key, (String) value);
        } else if (value instanceof Integer) {
            mmkv.encode(key, (Integer) value);
        } else if (value instanceof Long) {
            mmkv.encode(key, (Long) value);
        } else if (value instanceof Double) {
            mmkv.encode(key, (Double) value);
        } else if (value instanceof Float) {
            mmkv.encode(key, (Float) value);
        } else if (value instanceof Boolean) {
            mmkv.encode(key, (Boolean) value);
        }
    }

    public void setByteArray(String key, byte[] array) {
        if (mmkv == null && array == null || array.length <= 0) {
            return;
        }
        mmkv.encode(key, array);
    }

    public void setParcelable(String key, Parcelable tCalss) {
        if (mmkv == null && tCalss == null) {
            return;
        }
        mmkv.encode(key, tCalss);
    }

    public void setStringSet(String key, Set<String> setStr) {
        if (mmkv == null && setStr == null || setStr.size() <= 0) {
            return;
        }
        mmkv.encode(key, setStr);
    }

    //===========================    读取数据(暂时写常用参数)   ===============================

    public String getDecodeString(String key) {
        return mmkv.decodeString(key, "");
    }

    public Integer getDecodeInt(String key) {
        return mmkv.decodeInt(key, 0);
    }

    public Boolean getDecodeBool(String key) {
        return mmkv.decodeBool(key, false);
    }

    public void removeKey(String key) {
        if (mmkv != null) {
            mmkv.removeValueForKey(key);
        }
    }

    public void clearAll() {
        if (mmkv != null) {
            mmkv.clearAll();
        }
    }


}
