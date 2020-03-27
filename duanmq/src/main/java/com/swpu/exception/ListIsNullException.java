package com.swpu.exception;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午6:31
 **/

public class ListIsNullException extends RuntimeException{
    public ListIsNullException(String message) {
        super(message);
    }
}

