package com.inkhjw.personalcommunity.bean;

public class AddressListBean {
    private String name;
    private String phone;
    private String province;
    private String city;
    private String qu;
    private String detailAddress;
    private boolean isDefault;

    public AddressListBean(String name, String phone, String province, String city, String qu, String detailAddress, boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.qu = qu;
        this.detailAddress = detailAddress;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
