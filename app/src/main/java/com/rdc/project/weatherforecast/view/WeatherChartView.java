package com.rdc.project.weatherforecast.view;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rdc.project.weatherforecast.R;
import com.rdc.project.weatherforecast.bean.DailyForecastBean;
import com.rdc.project.weatherforecast.bean.WeatherBean;
import com.rdc.project.weatherforecast.utils.DensityUtil;
import com.rdc.project.weatherforecast.utils.MeasureUtil;
import com.rdc.project.weatherforecast.utils.TimeUtil;
import com.rdc.project.weatherforecast.utils.WeatherUtil;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class WeatherChartView extends LinearLayout {

    private List<DailyForecastBean> mDailyForecastBeanList = new ArrayList<>();

    private LayoutParams mCellParams;
    private LayoutParams mRowParams;
    private LayoutParams mChartParams;

    private LayoutTransition mLayoutTransition = new LayoutTransition();

    public WeatherChartView(Context context) {
        this(context, null);
    }

    public WeatherChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        mLayoutTransition.enableTransitionType(LayoutTransition.APPEARING);
        this.setLayoutTransition(mLayoutTransition);
        mRowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mCellParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        mChartParams = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, getContext()));
    }

    public void setDailyForecastBeanList(List<DailyForecastBean> dailyForecastBeanList) {
        mDailyForecastBeanList.clear();
        mDailyForecastBeanList.addAll(dailyForecastBeanList);
        start();
    }

    private void start() {
        removeAllViews();
        final LinearLayout dateTitleView = new LinearLayout(getContext());
        dateTitleView.setLayoutParams(mRowParams);
        dateTitleView.setOrientation(HORIZONTAL);
        dateTitleView.setLayoutTransition(mLayoutTransition);
        dateTitleView.removeAllViews();

        final LinearLayout iconView = new LinearLayout(getContext());
        iconView.setLayoutParams(mRowParams);
        iconView.setOrientation(HORIZONTAL);
        iconView.setLayoutTransition(mLayoutTransition);
        iconView.removeAllViews();

        final LinearLayout weatherStrView = new LinearLayout(getContext());
        weatherStrView.setLayoutParams(mRowParams);
        weatherStrView.setOrientation(HORIZONTAL);
        weatherStrView.setLayoutTransition(mLayoutTransition);
        weatherStrView.removeAllViews();

        List<Integer> minTemp = new ArrayList<>();
        List<Integer> maxTemp = new ArrayList<>();

        for (int i = 0; i < mDailyForecastBeanList.size(); i++) {
            final TextView tvDate = new TextView(getContext());
            tvDate.setGravity(Gravity.CENTER);
            tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tvDate.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvDate.setVisibility(INVISIBLE);
            final TextView tvWeather = new TextView((getContext()));
            tvWeather.setGravity(Gravity.CENTER);
            tvWeather.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tvWeather.setTextColor(getResources().getColor(R.color.colorPrimary));
            tvWeather.setVisibility(INVISIBLE);
            final ImageView ivIcon = new ImageView(getContext());
            ivIcon.setAdjustViewBounds(true);
            ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int padding = DensityUtil.dp2px(10, getContext());
            int width = MeasureUtil.getScreenWidth(getContext()) / mDailyForecastBeanList.size();
            int height = width;
            LayoutParams ivParam = new LayoutParams(width, height);
            ivParam.weight =1;
            ivIcon.setLayoutParams(ivParam);
            ivIcon.setPadding(padding, padding, padding, padding);
            ivIcon.setVisibility(INVISIBLE);

            if (i == 0) {
                tvDate.setText("今天");
            } else if (i == 1) {
                tvDate.setText("明天");
            } else {
                tvDate.setText(TimeUtil.getWeek(mDailyForecastBeanList.get(i).getDate(),
                        TimeUtil.DATE_SDF));
            }
            tvWeather.setText(mDailyForecastBeanList.get(i).getCond_txt_d());

            WeatherUtil.getInstance().getWeatherBean(mDailyForecastBeanList.get(i).getCond_code_d())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<WeatherBean>() {
                        @Override
                        public void call(WeatherBean weatherBean) {
                            Glide.with(getContext()).load(weatherBean.getIcon())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(ivIcon);
                        }
                    });
            minTemp.add(Integer.valueOf(mDailyForecastBeanList.get(i).getTmp_min()));
            maxTemp.add(Integer.valueOf(mDailyForecastBeanList.get(i).getTmp_max()));
            weatherStrView.addView(tvWeather, mCellParams);
            dateTitleView.addView(tvDate, mCellParams);
            iconView.addView(ivIcon);
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvDate.setVisibility(VISIBLE);
                    tvWeather.setVisibility(VISIBLE);
                    ivIcon.setVisibility(VISIBLE);
                }
            }, 200 * i);
        }
        addView(dateTitleView);
        addView(iconView);
        addView(weatherStrView);
        final ChartView chartView = new ChartView(getContext());
        chartView.setData(minTemp, maxTemp);
        chartView.setPadding(0, DensityUtil.dp2px(16, getContext()), 0, DensityUtil.dp2px(16, getContext()));
        addView(chartView, mChartParams);
    }
}
