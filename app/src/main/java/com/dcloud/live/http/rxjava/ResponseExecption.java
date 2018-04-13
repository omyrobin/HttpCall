package com.dcloud.live.http.rxjava;

/**
 * 后台成功返回数据，但数据状态码非成功状态，用来Observable.error抛出异常以便进行后续处理
 * Created by wubo on 2018/4/12.
 */

public class ResponseExecption extends Exception {

    private int code;

    public ResponseExecption(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
