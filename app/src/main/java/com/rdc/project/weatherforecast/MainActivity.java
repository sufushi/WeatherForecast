package com.rdc.project.weatherforecast;

import android.support.v4.view.ViewPager;

import com.rdc.project.weatherforecast.adapter.WeatherVpAdapter;
import com.rdc.project.weatherforecast.base.BaseActivity;
import com.rdc.project.weatherforecast.behavior.ZoomOutPageTransformer;
import com.rdc.project.weatherforecast.fragment.WeatherFragment;
import com.rdc.project.weatherforecast.listener.SelectCityCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements SelectCityCallback{

    @BindView(R.id.vp_weather)
    ViewPager mVpWeather;

    private List<WeatherFragment> mWeatherFragmentList;
    private WeatherVpAdapter mWeatherVpAdapter;

    private Map<String, Integer> mMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        String city = getIntent().getStringExtra("location");
        mWeatherFragmentList = new ArrayList<>();
        mWeatherFragmentList.add(WeatherFragment.newInstance(city));
        mMap.put(city, mWeatherFragmentList.size() - 1);
        mWeatherVpAdapter = new WeatherVpAdapter(getSupportFragmentManager(), mWeatherFragmentList);
        mVpWeather.setAdapter(mWeatherVpAdapter);
        mVpWeather.setPageTransformer(true, new ZoomOutPageTransformer());
        mVpWeather.setOffscreenPageLimit(mWeatherFragmentList.size());
    }

    @Override
    public void onSelectCity(String city) {
        if (mMap.containsKey(city)) {
            int value = mMap.get(city);
            mVpWeather.setCurrentItem(value);
        } else {
            mWeatherFragmentList.add(WeatherFragment.newInstance(city));
            mWeatherVpAdapter.notifyDataSetChanged();
            mVpWeather.setCurrentItem(mWeatherFragmentList.size() - 1);
            mVpWeather.setOffscreenPageLimit(mWeatherFragmentList.size());
            mMap.put(city, mWeatherFragmentList.size() - 1);
        }
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
