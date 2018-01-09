package com.rdc.project.weatherforecast.utils;

import com.google.gson.reflect.TypeToken;
import com.rdc.project.weatherforecast.app.App;
import com.rdc.project.weatherforecast.bean.WeatherBean;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class WeatherUtil {

    private static WeatherUtil sInstance;

    private Map<String, WeatherBean> weatherBeanMap;

    private WeatherUtil() {
        weatherBeanMap = new HashMap<>();
        List<WeatherBean> list = RetrofitManager.gson().fromJson(readFromAssets(),
                new TypeToken<List<WeatherBean>>() {
                }.getType());
        for (WeatherBean bean : list) {
            weatherBeanMap.put(bean.getCode(), bean);
        }
    }

    public static WeatherUtil getInstance() {
        if (sInstance == null) {
            synchronized (WeatherUtil.class) {
                sInstance = new WeatherUtil();
            }
        }
        return sInstance;
    }

    private String readFromAssets() {
        try {
            InputStream is = App.getContext().getAssets().open("weather.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<WeatherBean> getWeatherBean(final String code) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<WeatherBean>() {
            @Override
            public void call(Subscriber<? super WeatherBean> subscriber) {
                subscriber.onNext(WeatherUtil.getInstance().weatherBeanMap.get(code));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

}
