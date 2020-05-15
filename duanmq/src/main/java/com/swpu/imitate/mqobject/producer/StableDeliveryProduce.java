package com.swpu.imitate.mqobject.producer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.topic.NotRepeatConsumeTopic;
import com.swpu.model.Result;

import java.util.Date;
import java.util.Random;

/**
 * @author linyin
 * @date 2020/5/15
 * @time 下午3:35
 **/

public class StableDeliveryProduce{
    // 生产者名称
    private String producerName = "default";

    // 当确认收到时会被控制生产者的线程变为true
    private boolean receive = false;

    public StableDeliveryProduce() {
    }

    public StableDeliveryProduce(String producerName) {
        this.producerName = producerName;
    }

    public void produce(Message message) {
        // 确认收到做关闭
        receiveSwitch(false);
        message.setProducerName(this.producerName);
        message.setProduceTime(new Date());
        // offset做消息的唯一标示
        message.setOffset(NotRepeatConsumeTopic.offset.addAndGet(1));

        while (!this.receive){

            // 发送时模拟1/4可能性出现丢消息情况
            if (Math.abs(new Random().nextInt()) % 4 != 0){
                // 线程需要自己创建message传入，并通过等待其变为非null，表示收到数据，每隔1秒就会重新发送一次
                message.getExtension().put("ack","true");
            }

            try {
                // 休眠2秒,一是等待接收，二是控制流速
                Thread.sleep(2000);

                // 若未收到消息卡点控制3秒重发
                if (!this.receive){
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // receive开关
    public void receiveSwitch(boolean receive){
        this.receive = receive;

        // 收到时模拟1/4可能性出现丢消息情况
        if (receive && (Math.abs(new Random().nextInt()) % 4 == 0)){
            this.receive = false;
        }
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public boolean isReceive() {
        return receive;
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }
}

