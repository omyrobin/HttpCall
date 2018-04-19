package com.dcloud.live.http;

import android.support.annotation.Nullable;

import com.dcloud.live.http.config.BuildConfig;
import com.dcloud.live.http.config.UrlConfig;
import com.dcloud.live.http.interceptor.ClientInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wubo on 2018/3/28.
 */

public class ApiClient {
    // 静态内部类
    private static class HttpClient {

        private static OkHttpClient CLIENT;

        static {
            if(CLIENT == null){
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //应用拦截器设置
                builder.addInterceptor(new ClientInterceptor());
                //设置超时
                builder.connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS);
                builder.readTimeout(BuildConfig.READ_TIMEOUT, TimeUnit.SECONDS);
                builder.writeTimeout(BuildConfig.WRITETIMEOUT, TimeUnit.SECONDS);
                //Cookie持久化
//                builder.cookieJar(new CookieManger(App.getInstance().getApplicationContext()));
                //Cookie
                builder.cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });
                if (BuildConfig.DEBUG) {
                    //Log信息拦截器
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    //网络拦截器设置
                    builder.addNetworkInterceptor(loggingInterceptor);
                }
                //401token过期
                builder.authenticator(new Authenticator() {
                    @Nullable
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        //TODO 重新请求token
                        return null;
                    }
                });
                //错误重连
                builder.retryOnConnectionFailure(true);
                CLIENT = builder.build();
            }
        }
    }

    private synchronized static OkHttpClient getOkHttpClient(){
        return HttpClient.CLIENT;
    }

    public static Retrofit retrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.BASEURL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}