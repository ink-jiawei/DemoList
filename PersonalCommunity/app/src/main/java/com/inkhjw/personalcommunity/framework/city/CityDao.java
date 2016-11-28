package com.inkhjw.personalcommunity.framework.city;

import com.inkhjw.personalcommunity.framework.city.bean.City;
import com.inkhjw.personalcommunity.framework.city.bean.Province;

import java.util.List;


public interface CityDao {

    /**
     * 获取城市
     *
     * @return
     */
    List<City> queryCity(int code);

    /**
     * 获取县区
     *
     * @return
     */
    List<String> queryDistrict(int code);

    /**
     * 获取所有的省
     *
     * @return
     */
    List<Province> queryProvince();
}
