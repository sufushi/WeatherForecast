package com.rdc.project.weatherforecast.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rdc.project.weatherforecast.R;
import com.rdc.project.weatherforecast.adapter.LifeSuggestionRvAdapter;
import com.rdc.project.weatherforecast.adapter.WeatherHourlyRvAdapter;
import com.rdc.project.weatherforecast.base.BaseFragment;
import com.rdc.project.weatherforecast.bean.BasicBean;
import com.rdc.project.weatherforecast.bean.HeWeather6Bean;
import com.rdc.project.weatherforecast.bean.HourlyBean;
import com.rdc.project.weatherforecast.bean.NowBean;
import com.rdc.project.weatherforecast.bean.Weather;
import com.rdc.project.weatherforecast.bean.WeatherHourly;
import com.rdc.project.weatherforecast.listener.SelectCityCallback;
import com.rdc.project.weatherforecast.service.WeatherHourlyInterface;
import com.rdc.project.weatherforecast.service.WeatherInterface;
import com.rdc.project.weatherforecast.ui.SelectCityActivity;
import com.rdc.project.weatherforecast.utils.Cn2PinyinUtil;
import com.rdc.project.weatherforecast.utils.DensityUtil;
import com.rdc.project.weatherforecast.utils.RetrofitManager;
import com.rdc.project.weatherforecast.utils.ShareUtil;
import com.rdc.project.weatherforecast.view.WeatherChartView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.rdc.project.weatherforecast.constant.Constant.BASE_CITY_IMAGE_URL;
import static com.rdc.project.weatherforecast.constant.Constant.BASE_HE_WEATHER_URL;
import static com.rdc.project.weatherforecast.constant.Constant.EXPAND_CITY_IMAGE_URL;
import static com.rdc.project.weatherforecast.constant.Constant.HE_WEATHER_KEY;

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.image_switcher)
    ImageSwitcher mImageSwitcher;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_weather)
    TextView mTvWeather;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;
    @BindView(R.id.contentLayout)
    LinearLayout mContentLayout;
    @BindView(R.id.rv_suggestion)
    RecyclerView mRvSuggestion;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_weather_hourly)
    RecyclerView mRvWeatherHourly;
    @BindView(R.id.nsv_weather_hourly)
    NestedScrollView mNsvWeatherHourly;

    private String mCity;
    private String mPinyinCity;

    private int mIndex = 0;

    private Subscription mSubscription;
    private List<String> mImageUrlList;

    private HeWeather6Bean mHeWeather6Bean;

    private LifeSuggestionRvAdapter mLifeSuggestionRvAdapter;
    private WeatherHourlyRvAdapter mWeatherHourlyRvAdapter;

    private SelectCityCallback mSelectCityCallback;

    private BottomSheetBehavior mBottomSheetBehavior;

    public static WeatherFragment newInstance(String city) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("city", city);
        weatherFragment.setArguments(bundle);
        return weatherFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initData(Bundle bundle) {
        mLifeSuggestionRvAdapter = new LifeSuggestionRvAdapter(mBaseActivity);
        mWeatherHourlyRvAdapter = new WeatherHourlyRvAdapter(mBaseActivity);
        mCity = bundle.getString("city");
        mPinyinCity = Cn2PinyinUtil.getPinyin(mCity);
        showRefreshing(true);
        getCityImageList();
        getWeather();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectCityCallback = (SelectCityCallback) mBaseActivity;
    }

    private void getWeather() {
        mSubscription = RetrofitManager.getInstance().setBaseUrl(BASE_HE_WEATHER_URL)
                .create(WeatherInterface.class)
                .getWeather(mCity, HE_WEATHER_KEY)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Weather, HeWeather6Bean>() {
                    @Override
                    public HeWeather6Bean call(Weather weather) {
                        return weather.getHeWeather6().get(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HeWeather6Bean>() {

                    @Override
                    public void onCompleted() {
                        showRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showRefreshing(false);
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onNext(HeWeather6Bean heWeather6Bean) {
                        if (heWeather6Bean != null) {
                            showWeather(heWeather6Bean);
                        }
                    }
                });
    }

    private void showWeather(HeWeather6Bean heWeather6Bean) {
        mHeWeather6Bean = heWeather6Bean;
        mTvCity.setText(mHeWeather6Bean.getBasic().getLocation());
        mTvWeather.setText(mHeWeather6Bean.getNow().getCond_txt());
        mTvDegree.setText(String.format("%s℃", mHeWeather6Bean.getNow().getTmp()));
        mContentLayout.setPadding(0, DensityUtil.dp2px(16, getContext()), 0,
                DensityUtil.dp2px(16, getContext()));
        mContentLayout.removeAllViews();
        WeatherChartView chartView = new WeatherChartView(getContext());
        chartView.setDailyForecastBeanList(mHeWeather6Bean.getDaily_forecast());
        mContentLayout.addView(chartView);
        mLifeSuggestionRvAdapter.updataData(mHeWeather6Bean.getLifestyle());
    }

    private void getCityImageList() {
        final String url = BASE_CITY_IMAGE_URL + mPinyinCity + EXPAND_CITY_IMAGE_URL;
        mSubscription = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<String>>() {
                    @Override
                    public List<String> call(String s) {
                        List<String> list = new ArrayList<>();
                        try {
                            Document document = Jsoup.connect(url).timeout(10000).get();
                            Element element = document.select("div#slides").first();
                            Elements elements = element.select("img[src$=.jpg]");
                            for (Element e :
                                    elements) {
                                list.add(e.attr("src"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mImageUrlList = strings;
                        initImageSwitcher();
                    }
                });
    }

    private void initImageSwitcher() {
        mImageSwitcher.post(new Runnable() {
            @Override
            public void run() {
                loadImage();
            }
        });
        mSubscription = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        loadImage();
                    }
                });
    }

    private void loadImage() {
        if (mImageUrlList.size() > 0) {
            mIndex = ++mIndex % mImageUrlList.size();
            Glide.with(this).load(mImageUrlList.get(mIndex)).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource,
                                            GlideAnimation<? super GlideDrawable> glideAnimation) {
                    mImageSwitcher.setImageDrawable(resource);
                }
            });
        }
    }

    @Override
    protected void initView() {
        mToolbar.inflateMenu(R.menu.menu_weather);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mBaseActivity, SelectCityActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(mBaseActivity);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mBaseActivity, R.anim.zoom_in));
        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mBaseActivity, R.anim.zoom_out));
        mRvSuggestion.setLayoutManager(new LinearLayoutManager(mBaseActivity,
                LinearLayoutManager.VERTICAL, false));
        mRvSuggestion.setAdapter(mLifeSuggestionRvAdapter);
        mRvWeatherHourly.setLayoutManager(new LinearLayoutManager(mBaseActivity,
                LinearLayoutManager.VERTICAL, false));
        mRvWeatherHourly.setAdapter(mWeatherHourlyRvAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent,
                R.color.greenyellow, R.color.lightblue);
        mBottomSheetBehavior = BottomSheetBehavior.from(mNsvWeatherHourly);
        mBottomSheetBehavior.setHideable(true);
    }

    @Override
    protected void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                getCityImageList();
                getWeather();
            }
        });
        mToolbar.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                share();
                return true;
            }
        });
    }

    private void showRefreshing(final boolean refresh) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refresh);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mBaseActivity.getMenuInflater().inflate(R.menu.menu_weather, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                break;
            case R.id.setting:

                break;
            case R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        StringBuilder stringBuilder = new StringBuilder();
        BasicBean basicBean = mHeWeather6Bean.getBasic();
        stringBuilder.append("城市:").append(basicBean.getAdmin_area()).append(basicBean.getLocation()).append("\r\n");
        NowBean nowBean = mHeWeather6Bean.getNow();
        stringBuilder.append("天气:").append("\r\n").append(nowBean.getCond_txt()).append("  ").append(nowBean.getTmp()).append("°C").append("\r\n");
        stringBuilder.append("体感温度:").append(nowBean.getFl()).append("\r\n");
        stringBuilder.append("风向:").append(nowBean.getWind_deg()).append("°").append("  ").append(nowBean.getWind_dir()).append("\r\n");
        stringBuilder.append("风力:").append(nowBean.getWind_sc()).append("\r\n");
        stringBuilder.append("风速:").append(nowBean.getWind_spd()).append("\r\n");
        stringBuilder.append("相对湿度:").append(nowBean.getHum()).append("\r\n");
        stringBuilder.append("降水量:").append(nowBean.getPcpn()).append("\r\n");
        stringBuilder.append("大气压强:").append(nowBean.getPres()).append("\r\n");
        stringBuilder.append("能见度:").append(nowBean.getVis()).append("\r\n");
        stringBuilder.append("云量:").append(nowBean.getCloud()).append("\r\n");
        String share = stringBuilder.toString();
        Log.e("error", share);
        ShareUtil.shareText(mBaseActivity, share, "分享到");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                mSelectCityCallback.onSelectCity(data.getStringExtra("city_chosen"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    @OnClick({R.id.tv_city, R.id.tv_weather, R.id.tv_degree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
            case R.id.tv_weather:
            case R.id.tv_degree:
                getWeatherHourly();
                break;
        }
    }

    private void getWeatherHourly() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mWeatherHourlyRvAdapter.clear();
        mSubscription = RetrofitManager.getInstance().setBaseUrl(BASE_HE_WEATHER_URL).
                create(WeatherHourlyInterface.class)
                .getWeatherHourly(HE_WEATHER_KEY, mCity)
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherHourly, List<HourlyBean>>() {
                    @Override
                    public List<HourlyBean> call(WeatherHourly weatherHourly) {
                        return weatherHourly.getHeWeather6().get(0).getHourly();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HourlyBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onNext(List<HourlyBean> hourlyBeen) {
                        final LayoutAnimationController controller = AnimationUtils.
                                loadLayoutAnimation(mBaseActivity, R.anim.layout_animation_fall_down);
                        mRvWeatherHourly.setLayoutAnimation(controller);
                        mWeatherHourlyRvAdapter.updataData(hourlyBeen);
                        mRvWeatherHourly.scheduleLayoutAnimation();
                        Log.e("error", hourlyBeen.toString());
                    }
                });
    }
}
