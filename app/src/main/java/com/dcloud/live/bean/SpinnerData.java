package com.dcloud.live.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by omyrobin on 2017/4/14.
 */

public class SpinnerData extends BaseEntity implements Parcelable{

    /**国家**/
    private HashMap<String, String> country;
    /**证件类型**/
    private HashMap<String, String> visa_type;
    /**人员地域类型**/
    private HashMap<String, String> person_area_type;
    /**人员类型**/
    private HashMap<String, String> person_type;
    /**入境口岸**/
    private HashMap<String, String> entry_port;
    /**所属派出所**/
    private HashMap<String, String> police_station;
    /**所属社区**/
    private HashMap<String, HashMap<String, String>> community;
    /**停留事由**/
    private HashMap<String, String> stay_reason;
    /**签证（注）种类**/
    private HashMap<String, String> credential_type;
    /**职业**/
    private HashMap<String, String> occupation;
    /**住房种类**/
    private HashMap<String, String> house_type;

    public HashMap<String, String> getCountry() {
        return country;
    }

    public void setCountry(HashMap<String, String> country) {
        this.country = country;
    }

    public HashMap<String, String> getVisa_type() {
        return visa_type;
    }

    public void setVisa_type(HashMap<String, String> visa_type) {
        this.visa_type = visa_type;
    }

    public HashMap<String, String> getPerson_area_type() {
        return person_area_type;
    }

    public void setPerson_area_type(HashMap<String, String> person_area_type) {
        this.person_area_type = person_area_type;
    }

    public HashMap<String, String> getPerson_type() {
        return person_type;
    }

    public void setPerson_type(HashMap<String, String> person_type) {
        this.person_type = person_type;
    }

    public HashMap<String, String> getEntry_port() {
        return entry_port;
    }

    public void setEntry_port(HashMap<String, String> entry_port) {
        this.entry_port = entry_port;
    }

    public HashMap<String, String> getPolice_station() {
        return police_station;
    }

    public void setPolice_station(HashMap<String, String> police_station) {
        this.police_station = police_station;
    }

    public HashMap<String, HashMap<String, String>> getCommunity() {
        return community;
    }

    public void setCommunity(HashMap<String, HashMap<String, String>> community) {
        this.community = community;
    }

    public HashMap<String, String> getStay_reason() {
        return stay_reason;
    }

    public void setStay_reason(HashMap<String, String> stay_reason) {
        this.stay_reason = stay_reason;
    }

    public HashMap<String, String> getCredential_type() {
        return credential_type;
    }

    public void setCredential_type(HashMap<String, String> credential_type) {
        this.credential_type = credential_type;
    }

    public HashMap<String, String> getOccupation() {
        return occupation;
    }

    public void setOccupation(HashMap<String, String> occupation) {
        this.occupation = occupation;
    }

    public HashMap<String, String> getHouse_type() {
        return house_type;
    }

    public void setHouse_type(HashMap<String, String> house_type) {
        this.house_type = house_type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.country);
        dest.writeSerializable(this.visa_type);
        dest.writeSerializable(this.person_area_type);
        dest.writeSerializable(this.person_type);
        dest.writeSerializable(this.entry_port);
        dest.writeSerializable(this.police_station);
        dest.writeSerializable(this.community);
        dest.writeSerializable(this.stay_reason);
        dest.writeSerializable(this.credential_type);
        dest.writeSerializable(this.occupation);
        dest.writeSerializable(this.house_type);
    }

    public SpinnerData() {
    }

    protected SpinnerData(Parcel in) {
        this.country = (HashMap<String, String>) in.readSerializable();
        this.visa_type = (HashMap<String, String>) in.readSerializable();
        this.person_area_type = (HashMap<String, String>) in.readSerializable();
        this.person_type = (HashMap<String, String>) in.readSerializable();
        this.entry_port = (HashMap<String, String>) in.readSerializable();
        this.police_station = (HashMap<String, String>) in.readSerializable();
        this.community = (HashMap<String, HashMap<String, String>>) in.readSerializable();
        this.stay_reason = (HashMap<String, String>) in.readSerializable();
        this.credential_type = (HashMap<String, String>) in.readSerializable();
        this.occupation = (HashMap<String, String>) in.readSerializable();
        this.house_type = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<SpinnerData> CREATOR = new Creator<SpinnerData>() {
        @Override
        public SpinnerData createFromParcel(Parcel source) {
            return new SpinnerData(source);
        }

        @Override
        public SpinnerData[] newArray(int size) {
            return new SpinnerData[size];
        }
    };
}
