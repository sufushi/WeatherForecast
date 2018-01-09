package com.rdc.project.weatherforecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rdc.project.weatherforecast.R;
import com.rdc.project.weatherforecast.base.BaseRecyclerViewAdapter;
import com.rdc.project.weatherforecast.bean.HourlyBean;
import com.rdc.project.weatherforecast.bean.WeatherBean;
import com.rdc.project.weatherforecast.utils.WeatherUtil;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class WeatherHourlyRvAdapter extends BaseRecyclerViewAdapter<HourlyBean> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;

    public WeatherHourlyRvAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_weather_hourly, null);
        return new WeatherHourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherHourlyViewHolder weatherHourlyViewHolder = (WeatherHourlyViewHolder) holder;
        if (getItemViewType(position) == TYPE_TOP) {
            weatherHourlyViewHolder.mTvTopLine.setVisibility(View.INVISIBLE);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            weatherHourlyViewHolder.mTvTopLine.setVisibility(View.VISIBLE);
        }
        weatherHourlyViewHolder.bindView(mDataList.get(position));
    }

    class WeatherHourlyViewHolder extends BaseRvHolder {

        @BindView(R.id.tv_top_line)
        TextView mTvTopLine;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.iv_weather)
        ImageView mIvWeather;
        @BindView(R.id.tv_weather_txt)
        TextView mTvWeatherTxt;

        public WeatherHourlyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(HourlyBean hourlyBean) {
            mTvTime.setText(hourlyBean.getTime());
            String weather = hourlyBean.getCond_txt() + "  " + hourlyBean.getTmp() + "°C";
            mTvWeatherTxt.setText(weather);
            WeatherUtil.getInstance().getWeatherBean(hourlyBean.getCond_code())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<WeatherBean>() {
                        @Override
                        public void call(WeatherBean weatherBean) {
                            Glide.with(mContext).load(weatherBean.getIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mIvWeather);
                        }
                    });
        }
    }
}
