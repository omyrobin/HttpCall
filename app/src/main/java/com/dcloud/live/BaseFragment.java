package com.dcloud.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by wubo on 2018/3/27.
 */

public abstract class BaseFragment<V extends BaseView,T extends BasePresenter<V>> extends RxFragment{

    View contentView;

    T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getLayoutId(), container, false);
        presenter = initPresenter();
        return contentView;
    }

    public abstract int getLayoutId();

    public abstract T initPresenter();

    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V)this);
    }

    @Override
    public void onDestroyView() {
        presenter.dettach();
        super.onDestroyView();
    }

}
