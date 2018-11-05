package com.ethioroot.selfcare.ethioselfcare;

/**
 * Created by Lincoln on 18/05/16.
 */
public class AppsMenu {
    private String name;
    private String period;
    private String phoneNum;
    private String thumbnail;

    public AppsMenu() {
    }

    public AppsMenu(String name, String phoneNumber , String period, String thumbnail) {
        this.name = name;
        this.phoneNum = phoneNumber;
        this.thumbnail = thumbnail;
        this.period=period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
