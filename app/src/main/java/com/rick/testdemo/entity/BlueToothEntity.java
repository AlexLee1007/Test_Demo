package com.rick.testdemo.entity;

/**
 * package: BlueToothEntity
 * author: Rick Li
 * date: 2020/4/22 11:06
 * desc:
 */
public class BlueToothEntity {

    private String btName;

    private String btAddress;


    public String getBtName() {
        return btName;
    }

    public void setBtName(String btName) {
        this.btName = btName;
    }

    public String getBtAddress() {
        return btAddress;
    }

    public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
    }

    @Override
    public String toString() {
        return "BlueToothEntity{" +
                "btName='" + btName + '\'' +
                ", btAddress='" + btAddress + '\'' +
                '}';
    }
}
