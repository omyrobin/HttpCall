package com.dcloud.live.home;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.dcloud.live.BaseActivity;
import com.dcloud.live.R;

public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View{

    private MainContract.Presenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainContract.Presenter initPresenter() {
        presenter = new MainPresenter();
        return presenter;
    }

    @Override
    public void initializeView() {
        setContentView(R.layout.activity_main);
    }


    public void testApi(View view) {
        presenter.getSpinnerData("config","zh-CN");
    }

    public void cacelApi(View view) {
        presenter.cancelSpinnerData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setView() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        presenter.cancelSpinnerData();
        return super.onKeyDown(keyCode, event);
    }
}
