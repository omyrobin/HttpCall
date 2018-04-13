package com.dcloud.live.http.rxjava;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author RedLi
 * @date 2018/3/21
 * <p>
 * 请求失败的原因
 */

public class ApiException {

    /**
     * 解析数据失败
     **/
    public static final String PARSE_ERROR = "parse_error";

    /**
     * 网络问题
     **/
    public static final String BAD_NETWORK = "bad_network";

    /**
     * 连接错误
     **/
    public static final String CONNECT_ERROR = "connect_error";

    /**
     * 连接超时
     **/
    public static final String CONNECT_TIMEOUT = "connect_timeout";

    /**
     * section token past due 过期
     **/
    public static final String TOKEN_PAST_DUE = "token_past_due";

    /**
     * baseEntity中的状态码非成功
     **/
    public static final String DATA_ERROR = "data_error";

    /**
     * 未知错误
     **/
    public static final String UNKNOWN_ERROR = "unknown_error";

    @StringDef({PARSE_ERROR, BAD_NETWORK, CONNECT_ERROR, CONNECT_TIMEOUT, TOKEN_PAST_DUE, DATA_ERROR, UNKNOWN_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Error {}


}
