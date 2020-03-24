package com.swpu.util;

import java.io.UnsupportedEncodingException;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午7:06
 * 字符串与字节数组相互转换
 **/

public class StringByteSwitch {

    public static byte[] stringToByteArr(String s) throws UnsupportedEncodingException {
        return s.getBytes("UTF-8");
    }

    public static String byteArrToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    public static String byteArrToString(byte[] bytes, int start, int end) throws UnsupportedEncodingException {
        return new String(bytes, start, end, "UTF-8");
    }

}

