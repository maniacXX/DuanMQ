package com.swpu.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:38
 **/
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 489809L;
    private boolean success = false;
    private T result;

    public Result() {

    }

    public Result(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public static <T> Result<T> success(T obj) {
        Result<T> ret = new Result<T>();
        ret.setSuccess(true);
        ret.setResult(obj);
        return ret;
    }

    public static <T> Result<T> fail(T obj) {
        Result<T> ret = new Result<T>();
        ret.setSuccess(false);
        ret.setResult(obj);
        return ret;
    }
}


