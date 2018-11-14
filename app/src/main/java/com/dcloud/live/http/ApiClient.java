package com.dcloud.live.http;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dcloud.live.App;
import com.dcloud.live.BuildConfig;
import com.dcloud.live.http.config.OkBuildConfig;
import com.dcloud.live.http.config.UrlConfig;
import com.dcloud.live.http.cookie.CookieManger;
import com.dcloud.live.http.interceptor.ClientInterceptor;
import com.dcloud.live.http.ssl.TrustAllCerts;
import com.dcloud.live.http.ssl.TrustMyCerts;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
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
    /**
     * 静态内部类单例
     */
    private static class HttpClient {

        private static OkHttpClient CLIENT;

        static {
            if (CLIENT == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //支持信任所有证书https
//                builder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), new TrustAllCerts());
//                builder.hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier());
                //支持https信任指定服务器的证书
                try {
                    SSLContext sslContext =  new TrustMyCerts().setCertificates(App.getInstance().getAssets().open("lh_server.cer"));
                    builder.sslSocketFactory(sslContext.getSocketFactory(),new TrustMyCerts());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                builder.hostnameVerifier(new TrustMyCerts.TrustMyHostnameVerifier());
                //应用拦截器设置
                builder.addInterceptor(new ClientInterceptor());
                //登录头信息补充
//                builder.addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request;
//                        if(!TextUtils.isEmpty(App.getInstance().getToken())){
//                            request = chain.request()
//                                    .newBuilder()
//                                    .addHeader(HEADER_AUTHORIZATION, "Bearer " + App.getInstance().getToken())
//                                    .addHeader(HEADER_CONTENT_TYPE, "application/json; charset=UTF-8")
//                                    .addHeader(HEADER_ACCEPT, "application/json")
//                                    .build();
//                        }else {
//                            request = chain.request()
//                                    .newBuilder()
//                                    .addHeader(HEADER_CONTENT_TYPE, "application/json; charset=UTF-8")
//                                    .addHeader(HEADER_ACCEPT, "application/json")
//                                    .build();
//                        }
//                        Response response = chain.proceed(request);
//                        return response;
//                    }
//                });
                //设置超时
                builder.connectTimeout(OkBuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS);
                builder.readTimeout(OkBuildConfig.READ_TIMEOUT, TimeUnit.SECONDS);
                builder.writeTimeout(OkBuildConfig.WRITETIMEOUT, TimeUnit.SECONDS);
                //Cookie持久化
                builder.cookieJar(new CookieManger(App.getInstance().getApplicationContext()));
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

    private synchronized static OkHttpClient getOkHttpClient() {
        return HttpClient.CLIENT;
    }

    private static Retrofit retrofit;

    public static Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConfig.BASEURL)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
