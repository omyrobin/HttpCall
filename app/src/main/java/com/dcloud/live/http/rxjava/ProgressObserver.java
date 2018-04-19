package com.dcloud.live.http.rxjava;

import android.content.Context;
import android.net.ParseException;

import com.dcloud.live.R;
import com.dcloud.live.bean.BaseEntity;
import com.dcloud.live.http.progress.ProgressCancelListener;
import com.dcloud.live.http.progress.ProgressDialogHandler;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by wubo on 2018/4/9.
 */

public class ProgressObserver<T extends BaseEntity> implements Observer<T> {

    private SubscriberListener<T> subscriber;

    private Context context;

    private ProgressDialogHandler handler;

    private Disposable disposable;

    public ProgressObserver(Context context, SubscriberListener<T> subscriber) {
        this.subscriber = subscriber;
        this.context = context;

        handler = new ProgressDialogHandler(context, new ProgressCancelListener() {
            @Override
            public void onCancelProgress() {
                //取消订阅
                if(!disposable.isDisposed()){
                    disposable.dispose();
                }
            }
        }, true);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(T t) {
        subscriber.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        // 数据返回非正确状态码
        if (e instanceof ResponseExecption) {
            onResponseExecption(context, ((ResponseExecption) e).getCode(), e.getMessage());
            return;
        }
        //其它由第三方抛出的异常
        if (e instanceof SocketException) {
            onException(context, ApiException.CONNECT_ERROR);
        } else if (e instanceof HttpException) {
            // HTTP错误
            onException(context, ApiException.BAD_NETWORK);
        } else if (e instanceof UnknownHostException) {
            // 连接错误
            onException(context, ApiException.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            // 连接超时
            onException(context, ApiException.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            // 解析错误
            onException(context, ApiException.PARSE_ERROR);
        } else {
            onException(context, ApiException.UNKNOWN_ERROR);
        }
        dismissDialog();
    }

    @Override
    public void onComplete() {
        dismissDialog();
    }

    /**
     * 根据后台状态码协议进行相应的操作
     *
     * @param context
     * @param code
     * @param msg
     */
    private void onResponseExecption(Context context, int code, String msg) {
        switch (code) {
            default:
                subscriber.onFail(msg);
                break;
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    private void onException(Context context, @ApiException.Error String reason) {
        switch (reason) {
            case ApiException.CONNECT_ERROR:
                subscriber.onFail(context.getString(R.string.connect_error));
                break;
            case ApiException.CONNECT_TIMEOUT:
                subscriber.onFail(context.getString(R.string.connect_timeout));
                break;
            case ApiException.BAD_NETWORK:
                subscriber.onFail(context.getString(R.string.bad_network));
                break;
            case ApiException.PARSE_ERROR:
                subscriber.onFail(context.getString(R.string.parse_error));
                break;
            default:
                subscriber.onFail(context.getString(R.string.unknown_error));
                break;
        }
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public void dismissDialog(){
        handler.sendEmptyMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG);
    }

    public void showDialog() {
        handler.sendEmptyMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG);
    }
}
