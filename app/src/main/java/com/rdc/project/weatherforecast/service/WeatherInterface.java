package com.rdc.project.weatherforecast.service;

import com.rdc.project.weatherforecast.bean.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.rdc.project.weatherforecast.constant.Constant.WEATHER;

public interface WeatherInterface {

    @GET(WEATHER)
    Observable<Weather> getWeather(@Query("location") String location, @Query("key") String key);

}
