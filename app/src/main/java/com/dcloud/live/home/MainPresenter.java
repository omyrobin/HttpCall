package com.dcloud.live.home;

import android.content.Context;
import android.widget.Toast;

import com.dcloud.live.bean.SpinnerData;
import com.dcloud.live.bean.TestBean;
import com.dcloud.live.http.rxjava.ProgressObserver;
import com.dcloud.live.http.rxjava.SubscriberListener;

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
    public void getSpinnerData(String config, String language) {
        mModel.getSpinnerData(config, language, new ProgressObserver<>(mView.getContext(), new SubscriberListener<SpinnerData>() {
            @Override
            public void onSuccess(SpinnerData spinnerData) {

            }

            @Override
            public void onFail(String err) {
                Toast.makeText(mView.getContext(), err, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public void login(String username, String password) {
        ProgressObserver observer = new ProgressObserver<>(mView.getContext(), new SubscriberListener<TestBean>() {
            @Override
            public void onSuccess(TestBean testBean) {
                Toast.makeText(mView.getContext(), testBean.getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String err) {
                Toast.makeText(mView.getContext(), err, Toast.LENGTH_SHORT).show();
            }
        });
        mModel.login(username, password,observer);
        addObserver(observer.getDisposable());
    }

    @Override
    public void cancel() {
        clear();
    }

}
