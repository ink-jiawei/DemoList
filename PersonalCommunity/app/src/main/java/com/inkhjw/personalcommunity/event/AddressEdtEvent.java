package com.inkhjw.personalcommunity.event;

public class AddressEdtEvent {
    public int statu;
    public int position;

    /**
     * 1:设为默认地址
     * 2:编辑
     * 3:删除
     *
     * @param statu
     */
    public AddressEdtEvent(int statu, int position) {
        this.statu = statu;
        this.position = position;
    }
}
