package com.inkhjw.personalcommunity.framework.city.bean;

public class City {

    private int sCode;
    private String cityName;

    public City() {
    }

    public City(int sCode, String cityName) {
        this.sCode = sCode;
        this.cityName = cityName;
    }

    public int getsCode() {
        return sCode;
    }

    public void setsCode(int sCode) {
        this.sCode = sCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
