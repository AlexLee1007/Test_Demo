package com.rick.testdemo.utlis;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.time.Instant;
import java.util.Random;

public class TokenUtil {

    private static Random random = new Random();

    /**
     * @param version  鉴权版本号
     * @param appId    应用ID，由baiduRTC提供，全局唯一，用于识别应用；
     * @param appKey   appId对应的appKey
     * @param roomName 房间名
     * @param uid      用户ID
     * @param expectTs 预期超时时间，单位秒，如果为0，则默认24小时
     * @return
     *
     *
     *
     *  version/appId/appKey/roomName/uid/expectTs 客户端提供
     *
     */
    public String constructToken(String version, String appId, String appKey, String roomName, String uid, int expectTs) {
        // 当前时间戳
        Instant t = Instant.now();
        long tSecond = t.getEpochSecond();
        String ts = complement(tSecond + "", 10);
        System.out.println("ts:" + ts);
        String expectTsStr = complement(expectTs + "", 10);

        // 随机一个整数，并转成对应的16进制字符串
        String randomString = Integer.toHexString(random.nextInt());
        randomString = complement(randomString, 8);
        System.out.println("randomString:" + randomString);
        // 使用HmacSHA1算法加密，计算签名，签名使用的key为appKey，签名的data为："ACS" + appID + ts + randomString + roomName + uid + expectTs
        String data = new StringBuilder().append("ACS").append(appId)
                .append(ts).append(randomString).append(roomName).append(uid).append(expectTsStr).toString();
        // String signature = MyHmacUtils.sha1(appKey, data);

        // 原使用方法
        //  String signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, appKey).hmacHex(data);
        //二次修改
        HmacUtils hu = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, appKey);
        String signature = new String(Hex.encodeHex(hu.hmac(data)));


        System.out.println("data:" + new StringBuilder().append("ACS").append(appId)
                .append(ts).append(randomString).append(roomName).append(uid).append(expectTsStr).toString());
        System.out.println("signature:" + signature);
        // 生成token
        return new StringBuilder().append(version).append(signature).append(ts)
                .append(randomString).append(expectTsStr).toString();
    }


    // 将字符串补0，达到目标长度
    public String complement(String str, int goalLength) {
        int length = str.length();
        if (length == goalLength) {
            return str;
        } else if (length < goalLength) {
            for (int i = 0; i < goalLength - length; i++) {
                str = "0" + str;
            }
            return str;
        } else {
            return str.substring(0, goalLength);
        }
    }

    public String complementBehind(String str, int goalLength) {
        int length = str.length();
        if (length == goalLength) {
            return str;
        } else if (length < goalLength) {
            for (int i = 0; i < goalLength - length; i++) {
                str = str + "0";
            }
            return str;
        } else {
            return str.substring(0, goalLength);
        }

    }

//    public static void main(String[] args) {
//        String version = "004";
//        String appKey = "s-jcagj2g5eewai6u3p3";
//        TokenUtil tokenUtil = new TokenUtil();
//        String token = tokenUtil.constructToken(version,"app-jcagj2g5ecrqv7bn", appKey, "aaa", "54321", 0);
//        System.out.println(token);
//    }


}
