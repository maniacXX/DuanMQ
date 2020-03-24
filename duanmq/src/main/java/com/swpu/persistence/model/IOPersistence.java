package com.swpu.persistence.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.swpu.config.ConstantConfig;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;
import com.swpu.util.StringByteSwitch;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午5:24
 **/

public class IOPersistence implements Persistence {
    // 操作文件
    private static File file = new File("duanmq/src/main/resource/storehouse/io/temp.txt");

    @Override
    public Result<Boolean> write(Message message) {
        try {
            synchronized (file){
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(StringByteSwitch.stringToByteArr(JSON.toJSONString(message) + ConstantConfig.messageSplit));
                fileOutputStream.close();
            }
            return Result.success(true);
        }catch (IOException e){
            e.printStackTrace();
        }
        return Result.fail(false);
    }

    public Result<Boolean> write(String s, Boolean append) {
        try {
            synchronized (file) {
                FileOutputStream fileOutputStream = new FileOutputStream(file, append);
                fileOutputStream.write(StringByteSwitch.stringToByteArr(s));
                fileOutputStream.close();
            }
            return Result.success(true);
        }catch (IOException e){
            e.printStackTrace();
        }
        return Result.fail(false);
    }

    @Override
    public Result<List<Message>> read() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            synchronized (file) {
                FileInputStream fileInputStream = new FileInputStream(file);

                byte[] buf = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buf)) != -1)
                    stringBuilder.append(StringByteSwitch.byteArrToString(buf, 0, length));
                fileInputStream.close();

                // 清空文件
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                fileOutputStream.write(StringByteSwitch.stringToByteArr(""));
                fileOutputStream.close();
            }

            String[] s = stringBuilder.toString().split(ConstantConfig.messageSplit);
            List<Message> result = new ArrayList<>();
            List<String> info = Arrays.asList(s);
            if (info.size() > 0){
                info = info.subList(0, info.size() - 1);
            }

            info.forEach(x -> {
                result.add(JSON.parseObject(x, new TypeReference<Message>(){}));
            });
            return Result.success(result);
        }catch (IOException e){
            e.printStackTrace();
        }
        return Result.fail(null);
    }
}

