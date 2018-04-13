package com.dcloud.live.api;

import com.dcloud.live.bean.BaseEntity;
import com.dcloud.live.bean.SpinnerData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * Created by wubo on 2018/3/28.
 */

public interface ApiService {
    @GET
    Observable<BaseEntity<SpinnerData>> getSpinnerData(@Url String config, @Header("Accept-Language") String language);
}
