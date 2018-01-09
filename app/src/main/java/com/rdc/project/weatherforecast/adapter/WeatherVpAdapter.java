package com.rdc.project.weatherforecast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.rdc.project.weatherforecast.fragment.WeatherFragment;

import java.util.List;

public class WeatherVpAdapter extends FragmentStatePagerAdapter {

    private List<WeatherFragment> mWeatherFragmentList;

    public WeatherVpAdapter(FragmentManager fm, List<WeatherFragment> weatherFragmentList) {
        super(fm);
        mWeatherFragmentList = weatherFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mWeatherFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mWeatherFragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void addWeatherFragment(WeatherFragment weatherFragment) {
        mWeatherFragmentList.add(weatherFragment);
        notifyDataSetChanged();
    }

    public void removeWeatherFragment(int position) {
        mWeatherFragmentList.remove(position);
        notifyDataSetChanged();
    }
}
