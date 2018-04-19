package com.dcloud.live.http.rxjava;

public interface SubscriberListener<T> {

    /**
     * 返回数据
     *
     * @param t the t
     */
    void onSuccess(T t);


    /**
     * On fail.
     * 失败
     *
     * @param err the err
     */
    void onFail(String err);
}
