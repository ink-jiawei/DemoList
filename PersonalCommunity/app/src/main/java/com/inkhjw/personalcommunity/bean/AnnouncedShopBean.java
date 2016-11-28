package com.inkhjw.personalcommunity.bean;

public class AnnouncedShopBean {

    private int shopImage;
    private String shopDescribe;
    private String qihao;//期号
    private long announcedTime;//揭晓时间

    public AnnouncedShopBean(int shopImage, String shopDescribe, String qihao, long announcedTime) {
        this.shopImage = shopImage;
        this.shopDescribe = shopDescribe;
        this.qihao = qihao;
        this.announcedTime = announcedTime;
    }

    public int getShopImage() {
        return shopImage;
    }

    public void setShopImage(int shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopDescribe() {
        return shopDescribe;
    }

    public void setShopDescribe(String shopDescribe) {
        this.shopDescribe = shopDescribe;
    }

    public String getQihao() {
        return qihao;
    }

    public void setQihao(String qihao) {
        this.qihao = qihao;
    }

    public long getAnnouncedTime() {
        return announcedTime;
    }

    public void setAnnouncedTime(long announcedTime) {
        this.announcedTime = announcedTime;
    }
}
