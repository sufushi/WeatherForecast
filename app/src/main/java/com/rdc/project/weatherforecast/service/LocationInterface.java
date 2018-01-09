package com.rdc.project.weatherforecast.service;

import com.rdc.project.weatherforecast.bean.LocationBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.rdc.project.weatherforecast.constant.Constant.IP;

public interface LocationInterface {

    @GET(IP)
    Observable<LocationBean> getLocation(@Query("key") String key, @Query("ip") String ip);

    @GET(IP)
    Observable<LocationBean> getLocation(@Query("key") String key);

}
