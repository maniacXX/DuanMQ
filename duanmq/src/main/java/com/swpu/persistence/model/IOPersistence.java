package com.swpu.persistence.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.swpu.config.ConstantConfig;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;
import com.swpu.util.IODeal;
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

public class IOPersistence extends Persistence {
    // 操作文件
    private static File file = new File("duanmq/src/main/resource/storehouse/io/temp.txt");

    @Override
    public Result<Boolean> write(Message message) {
        FileOutputStream fileOutputStream = null;
        synchronized (file){
            try {
                fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(StringByteSwitch.stringToByteArr(JSON.toJSONString(message) + ConstantConfig.messageSplit));
                return Result.success(true);
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                IODeal.close(fileOutputStream);
            }
            return Result.fail(false);
        }
    }

    @Override
    public Result<List<Message>> read() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        synchronized (file) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                fileInputStream = new FileInputStream(file);

                byte[] buf = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buf)) != -1)
                    stringBuilder.append(StringByteSwitch.byteArrToString(buf, 0, length));

                // 清空文件
                fileOutputStream = new FileOutputStream(file, false);
                fileOutputStream.write(StringByteSwitch.stringToByteArr(""));

                return Result.success(stringToMessageList(stringBuilder.toString()));
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                IODeal.close(fileInputStream);
                IODeal.close(fileOutputStream);
            }
            return Result.fail(null);
        }
    }
}

