package com.dcloud.live;

/**
 * Created by wubo on 2018/3/27.
 */

public abstract class BasePresenter<V extends BaseView> {

    public V mView;

    public void attach(V mView){
        this.mView = mView;
    }

    public void dettach(){
        mView = null;
    }
}
