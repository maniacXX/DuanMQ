package com.swpu.util;

import java.io.Closeable;

/**
 * @author linyin
 * @date 2020/3/24
 * @time 下午5:36
 **/

public class IODeal {
    public static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

