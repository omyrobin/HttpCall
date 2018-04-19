package com.dcloud.live.home;

import android.content.Context;

import com.dcloud.live.BasePresenter;
import com.dcloud.live.BaseView;

/**
 * Created by wubo on 2018/4/12.
 */

public interface MainContract {

    interface View extends BaseView {
        Context getContext();

        void setView();
    }

    abstract class Presenter extends BasePresenter<View>{

        public abstract void getSpinnerData(String config, String s);

        public abstract void login(String username, String password);

        public abstract void cancel();
    }
}
