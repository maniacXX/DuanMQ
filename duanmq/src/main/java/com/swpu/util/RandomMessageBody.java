package com.swpu.util;

import com.swpu.model.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午7:47
 **/

public class RandomMessageBody {
    public static Map randomCreateMessageBody(){
        Map<String,String> map = new HashMap();
        map.put("id", String.valueOf(UUID.randomUUID()));
        map.put("time", String.valueOf(new Date()));
        map.put("test1", String.valueOf(UUID.randomUUID()));
        map.put("test2", String.valueOf(UUID.randomUUID()));
        map.put("test3", String.valueOf(UUID.randomUUID()));
        map.put("test4", String.valueOf(UUID.randomUUID()));
        return map;
    }
}

