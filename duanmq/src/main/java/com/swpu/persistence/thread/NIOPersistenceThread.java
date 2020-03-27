package com.swpu.persistence.thread;

import com.alibaba.fastjson.JSON;
import com.swpu.config.ConstantConfig;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.producer.MQProducer;
import com.swpu.imitate.mqobject.producer.Producer;
import com.swpu.persistence.manager.NIOPersistenceManager;
import com.swpu.persistence.manager.PersistenceManager;
import com.swpu.util.RandomMessageBody;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linyin
 * @date 2020/3/24
 * @time 下午2:05
 **/
@Data
public class NIOPersistenceThread extends Thread{

    private static Producer producer = new MQProducer();

    private static NIOPersistenceManager nioPersistence = new NIOPersistenceManager();

    @Override
    public void run() {
        synchronized(nioPersistence){
            List<Message> messages = new ArrayList<>();
            System.out.println(super.getName() + ":随机生成50000条消息");
            for (int i = 0; i < 50000; i++){
                messages.add(producer.produce(RandomMessageBody.randomCreateMessageBody()).getResult());
                //System.out.println(messages.get(i).toString());
            }

            System.out.println(super.getName() + ":持久化开始！");
            long time1 = System.currentTimeMillis();

            StringBuilder stringBuilder = new StringBuilder();

            // 数据写入
            messages.forEach(message -> stringBuilder.append(JSON.toJSONString(message) + ConstantConfig.messageSplit));
            nioPersistence.write(stringBuilder.toString());

            System.out.println(super.getName() + ":写入成功！");
            long time2 = System.currentTimeMillis();
            System.out.println("开始读取数据！");

            // 读出数据
            messages = nioPersistence.read().getResult();

            System.out.println(super.getName() + ":读取成功！");
            long time3 = System.currentTimeMillis();
            System.out.println("写入数据开销(毫秒)：" + (time2 - time1));
            System.out.println("读取数据开销(毫秒)：" + (time3 - time2));
//            System.out.println("数据内容如下:");
//            messages.forEach(x-> System.out.println(x.toString()));
        }
    }
}

