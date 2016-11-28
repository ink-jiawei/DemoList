package com.inkhjw.personalcommunity.bean;

public class ShopListBean {
    private boolean type;
    private int shopImage;
    private String shopDescribe;
    private int allNeed;
    private int nextNeed;

    public ShopListBean(int shopImage, String shopDescribe, int allNeed, int nextNeed) {
        this.shopImage = shopImage;
        this.shopDescribe = shopDescribe;
        this.allNeed = allNeed;
        this.nextNeed = nextNeed;
    }

    public ShopListBean(boolean type, int shopImage, String shopDescribe, int allNeed, int nextNeed) {
        this.type = type;
        this.shopImage = shopImage;
        this.shopDescribe = shopDescribe;
        this.allNeed = allNeed;
        this.nextNeed = nextNeed;
    }


    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
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

    public int getAllNeed() {
        return allNeed;
    }

    public void setAllNeed(int allNeed) {
        this.allNeed = allNeed;
    }

    public int getNextNeed() {
        return nextNeed;
    }

    public void setNextNeed(int nextNeed) {
        this.nextNeed = nextNeed;
    }
}
