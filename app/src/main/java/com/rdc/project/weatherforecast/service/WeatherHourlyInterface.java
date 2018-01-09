package com.rdc.project.weatherforecast.service;

import com.rdc.project.weatherforecast.bean.WeatherHourly;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.rdc.project.weatherforecast.constant.Constant.WEATHER_HOURLY;

public interface WeatherHourlyInterface {

    @GET(WEATHER_HOURLY)
    Observable<WeatherHourly> getWeatherHourly(@Query("key") String key, @Query("location") String location);

}
