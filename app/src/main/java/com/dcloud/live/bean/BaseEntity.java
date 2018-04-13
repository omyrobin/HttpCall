package com.dcloud.live.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 根据自己的api数据格式进行相应的定制
 *
 * Created by wubo on 2018/4/10.
 */

public class BaseEntity<T extends Parcelable> implements Parcelable {

    private T data;

    private String msg;

    private int code;

    private int status_code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeInt(this.status_code);
    }

    public BaseEntity() {
    }

    protected BaseEntity(Parcel in) {
        String dataName = in.readString();
        try {
            this.data = in.readParcelable(Class.forName(dataName).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.msg = in.readString();
        this.code = in.readInt();
        this.status_code = in.readInt();
    }

    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel source) {
            return new BaseEntity(source);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };
}
