package com.dcloud.live.home;

import android.content.Context;
import android.widget.Toast;

import com.dcloud.live.BaseModel;
import com.dcloud.live.api.ApiService;
import com.dcloud.live.bean.BaseEntity;
import com.dcloud.live.bean.SpinnerData;
import com.dcloud.live.http.client.ApiClient;
import com.dcloud.live.http.HttpCall;
import com.dcloud.live.http.rxjava.ProgressObserver;
import com.dcloud.live.http.rxjava.SubscriberListener;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wubo on 2018/4/12.
 */

public class MainModel extends BaseModel<MainPresenter>{

    private  Observable<BaseEntity<SpinnerData>> observable;

    public void getSpinnerData(final Context context, String config, String s) {
        observable = ApiClient.retrofit().create(ApiService.class).getSpinnerData(config,s);
        HttpCall.request(observable, new ProgressObserver<>(context, new SubscriberListener<SpinnerData>() {
           @Override
           public void onSuccess(SpinnerData spinnerData) {

           }

           @Override
           public void onFail(String err) {
               Toast.makeText(context, err, Toast.LENGTH_SHORT).show();
           }
        }), true);
    }

    public void cancelSpinnerData() {
        observable.unsubscribeOn(Schedulers.io());
    }
}
