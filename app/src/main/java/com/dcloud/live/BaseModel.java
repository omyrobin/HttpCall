package com.dcloud.live;

/**
 * Created by wubo on 2018/4/12.
 */

public abstract class BaseModel<P extends BasePresenter> {

    private P presenter;

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        return presenter;
    }
}
