package com.swpu.exception;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午6:31
 **/

public class TopicIsNullException extends RuntimeException{
    public TopicIsNullException(String message) {
        super(message);
    }
}

