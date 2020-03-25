package com.swpu.persistence.manager;

import com.alibaba.fastjson.JSON;
import com.swpu.config.ConstantConfig;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;
import com.swpu.util.IODeal;
import com.swpu.util.StringByteSwitch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午5:25
 **/

public class NIOPersistenceManager extends PersistenceManager {

    // 操作文件
    private static File file = new File("duanmq/src/main/resource/storehouse/nio/temp.txt");

    @Override
    public Result<Boolean> write(Message message){
        FileOutputStream fileOutputStream = null;
        synchronized (file){
            try {
                fileOutputStream = new FileOutputStream(file, true);
                FileChannel channel = fileOutputStream.getChannel();
                ByteBuffer byteBuffer = Charset.forName("utf8").encode(JSON.toJSONString(message) + ConstantConfig.messageSplit);
                while (channel.write(byteBuffer) != 0);
                channel.close();
                return Result.success(true);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IODeal.close(fileOutputStream);
            }
            return Result.fail(false);
        }
    }

    @Override
    public Result<List<Message>> read() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        synchronized (file){
            try {
                fileInputStream = new FileInputStream(file);
                FileChannel channel = fileInputStream.getChannel();

                // 缓存区一次读取的字节数
                int capacity = 1000;
                ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
                int length = channel.read(byteBuffer);
                StringBuilder stringBuilder = new StringBuilder();

                do{
                    // 重制缓存的位点来读取下一次的数据流
                    byteBuffer.clear();

                    stringBuilder.append(StringByteSwitch.byteArrToString(byteBuffer.array(), 0, length));
                } while ((length = channel.read(byteBuffer)) != -1);

                // 清空文件
                fileOutputStream = new FileOutputStream(file, false);
                channel = fileOutputStream.getChannel();
                byteBuffer = Charset.forName("utf8").encode("");
                while (channel.write(byteBuffer) != 0);
                channel.close();

                return Result.success(stringToMessageList(stringBuilder.toString()));
            }catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IODeal.close(fileOutputStream);
            }
            return Result.fail(null);
        }
    }
}

