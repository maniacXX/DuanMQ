package com.swpu.imitate.mqobject.consumer;

import lombok.Data;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:55
 * 消费结果消息体
 **/

@Data
public class ConsumeResult {
    // 消费结果
    private boolean success = false;

    // 消息偏移量
    private long offset;

    public ConsumeResult() {

    }

    public ConsumeResult(boolean success,long offset) {
        this.success = success;
        this.offset = offset;
    }

    public static ConsumeResult success(long offset) {
        ConsumeResult ret = new ConsumeResult();
        ret.setSuccess(true);
        ret.setOffset(offset);
        return ret;
    }

    public static ConsumeResult fail() {
        ConsumeResult ret = new ConsumeResult();
        ret.setSuccess(false);
        ret.setOffset(-1);
        return ret;
    }
}

