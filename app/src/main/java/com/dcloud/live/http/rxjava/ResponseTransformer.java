package com.dcloud.live.http.rxjava;


import android.os.Parcelable;

import com.dcloud.live.bean.BaseEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ResponseTransformer {
    /**
     * 对结果进行Transformer处理
     *
     * @param <T>
     * @return
     */
    public static <T extends Parcelable> ObservableTransformer<BaseEntity<T>, T> transformerResult() {
        return new ObservableTransformer<BaseEntity<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseEntity<T>> upstream) {
                return upstream.flatMap(new Function<BaseEntity<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseEntity<T> tBaseEntity) throws Exception {
                        if (tBaseEntity.getCode() == ResponseState.SUCCESS_STATE) {
                            return createData(tBaseEntity.getData());
                        } else {
                            return Observable.error(new ResponseExecption(tBaseEntity.getCode(), tBaseEntity.getMsg()));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return new Observable<T>() {
            @Override
            protected void subscribeActual(Observer<? super T> observer) {
                try {
                    observer.onNext(data);
                    observer.onComplete();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        };
    }


}