package com.dcloud.live.http;

import android.os.Parcelable;

import com.dcloud.live.bean.BaseEntity;
import com.dcloud.live.http.progress.ProgressDialogHandler;
import com.dcloud.live.http.rxjava.ProgressObserver;
import com.dcloud.live.http.rxjava.ResponseTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by wubo on 2018/4/10.
 */

public class HttpCall {

    public static <T extends Parcelable> void request(Observable<BaseEntity<T>> observable, final ProgressObserver observer, final boolean isShowDialog) {
        //数据转换BaseEntity<T>===> T
        ObservableTransformer<BaseEntity<T>, T> response = ResponseTransformer.transformerResult();

        observable.compose(response).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                if (isShowDialog) {
                    observer.showDialog();
                }
            }
        }).subscribe(observer);
    }
}
