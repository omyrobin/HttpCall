package com.dcloud.live.http.interceptor;

import android.util.Log;

import com.dcloud.live.http.config.BuildConfig;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wubo on 2018/4/9.
 */

public class ClientInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        //请求拦截(头信息添加)
        Request request = chain.request();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                Log.i("Content-Type: ", requestBody.contentType() + "");
            }
            if (requestBody.contentLength() != -1) {
                Log.i("Content-Length: ", requestBody.contentLength() + "");
            }
        }

        if (hasRequestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {
                Log.i("请求数据", buffer.readString(charset));
            }
        }

        request.newBuilder()
                .addHeader(BuildConfig.HEADER_CONTENT_TYPE, "application/json; charset=UTF-8")
                .addHeader(BuildConfig.HEADER_ACCEPT, "application/json")
                .build();
        //响应拦截(头信息获取)
        Response response = chain.proceed(request);
        //存入Session
//        if (response.header("Set-Cookie") != null) {
//            SessionManager.setSession(response.header("Set-Cookie"));
//        }
        //刷新API调用时间
//        SessionManager.setLastApiCallTime(System.currentTimeMillis());
        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
