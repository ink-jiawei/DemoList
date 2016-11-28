package com.inkhjw.personalcommunity.event;

public class CitySelectedEvent {
    public int type;//1:省、2:市、3:区
    public int code;
    public String name;

    public CitySelectedEvent(int type, int code,
                             String name) {
        this.type = type;
        this.code = code;
        this.name = name;
    }
}
