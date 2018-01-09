package com.rdc.project.weatherforecast.service;

import com.rdc.project.weatherforecast.bean.CityBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.rdc.project.weatherforecast.constant.Constant.LOCATION;

public interface CitySuggestionInterface {

    @GET(LOCATION)
    Observable<CityBean> getLocation(@Query("key") String key, @Query("q") String q);

}
