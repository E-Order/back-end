package com.eorder.utils;

import java.util.Random;

public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return
     */
    //synchronized 多线程
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        System.currentTimeMillis();
        //生成6位随机数
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
