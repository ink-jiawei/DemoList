package com.inkjiawei.retrofitdemo;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author hjw
 */
public interface WeatherApi {

    String WEATHER_API = "http://apis.baidu.com/heweather/weather/";
    String WEATHER_KEY = "2b4dbab11b908fc66f259edc37b2a176";//私人用

    String WEATHER = "free";

    /**
     * @param city 城市名称
     * @return
     */
    @GET(WEATHER)
    Call<ResponseBody> getWeather(@Query("city") String city);
}
