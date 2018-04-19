package com.dcloud.live;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wubo on 2018/3/27.
 */

public abstract class BasePresenter<V extends BaseView> {

    private CompositeDisposable mDisposables = new CompositeDisposable();

    public V mView;

    public void addObserver(Disposable observer){
        if(mDisposables != null){
            mDisposables.add(observer);
        }
    }

    public void clear(){
        if(mDisposables != null && !mDisposables.isDisposed()){
            mDisposables.clear();
        }
    }

    public void attach(V mView){
        this.mView = mView;
    }

    public void dettach(){
        clear();
        mView = null;
    }
}
