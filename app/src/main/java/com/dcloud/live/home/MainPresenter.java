package com.dcloud.live.home;

/**
 * Created by wubo on 2018/4/12.
 */

public class MainPresenter extends MainContract.Presenter {

    private MainModel mModel;

    public MainPresenter() {
        mModel = new MainModel();
        mModel.setPresenter(this);
    }

    @Override
    public void getSpinnerData(String config, String s) {
        mModel.getSpinnerData(mView.getContext(),config, s);
    }

    @Override
    public void cancelSpinnerData() {
        mModel.cancelSpinnerData();
    }

    @Override
    public void spinnerdata() {
        mView.setView();
    }
}
