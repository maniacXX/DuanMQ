package com.swpu.persistence.model;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午5:25
 **/

public class NIOPersistence implements Persistence {

    @Override
    public Result<Boolean> write(Message message) throws FileNotFoundException {
        return null;
    }

    @Override
    public Result<List<Message>> read() {
        return null;
    }
}

