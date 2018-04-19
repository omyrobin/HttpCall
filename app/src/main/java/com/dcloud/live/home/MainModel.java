package com.dcloud.live.home;

import android.content.Context;
import android.widget.Toast;

import com.dcloud.live.BaseModel;
import com.dcloud.live.api.ApiService;
import com.dcloud.live.bean.BaseEntity;
import com.dcloud.live.bean.SpinnerData;
import com.dcloud.live.bean.TestBean;
import com.dcloud.live.http.ApiClient;
import com.dcloud.live.http.HttpCall;
import com.dcloud.live.http.rxjava.ProgressObserver;
import com.dcloud.live.http.rxjava.SubscriberListener;

import io.reactivex.Observable;

/**
 * Created by wubo on 2018/4/12.
 */

public class MainModel extends BaseModel<MainPresenter>{

    public void getSpinnerData(String config, String  language, ProgressObserver observer) {
        Observable<BaseEntity<SpinnerData>> observable = ApiClient.retrofit().create(ApiService.class).getSpinnerData(config,language);
        HttpCall.request(observable, observer, true);
    }

    public void login(String username, String password, ProgressObserver observer) {
        Observable<BaseEntity<TestBean>> observable = ApiClient.retrofit().create(ApiService.class).login(username, password);
        HttpCall.request(observable, observer, true);
    }

}
