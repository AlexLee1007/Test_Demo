package com.rick.testdemo.entity;

import android.widget.Toast;

import java.io.Serializable;

/**
 * package: BaseEntity
 * author: Rick
 * date: 2020-04-03 12:37
 * desc: 实体类基类
 */


public class BaseEntity implements Serializable {

    private String code;

    private String desc;

    private String timestamp;

    private String data;

    private String sign;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data='" + data + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
