package com.rdc.project.weatherforecast.ui;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.library.ChooseEditText;
import com.library.OnChooseEditTextListener;
import com.rdc.project.weatherforecast.R;
import com.rdc.project.weatherforecast.adapter.CitySuggestionRvAdapter;
import com.rdc.project.weatherforecast.base.BaseActivity;
import com.rdc.project.weatherforecast.bean.CityBean;
import com.rdc.project.weatherforecast.listener.OnClickRecyclerViewListener;
import com.rdc.project.weatherforecast.service.CitySuggestionInterface;
import com.rdc.project.weatherforecast.utils.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.rdc.project.weatherforecast.constant.Constant.BASE_XIN_WEATHER_URL;
import static com.rdc.project.weatherforecast.constant.Constant.XIN_WEATHER_KEY;

public class SelectCityActivity extends BaseActivity implements OnClickRecyclerViewListener {

    @BindView(R.id.cet_city)
    ChooseEditText mCetCity;
    @BindView(R.id.tb_title)
    Toolbar mTbTitle;
    @BindView(R.id.rv_search_suggestion)
    RecyclerView mRvSearchSuggestion;

    private List<CityBean.ResultsBean> mResultsBeanList;
    private CitySuggestionRvAdapter mCitySuggestionRvAdapter;

    private List<CityBean.ResultsBean> mDefaultResultsBeanList;

//    private Subscription mSubscription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initData() {
        mDefaultResultsBeanList = new ArrayList<>();
        mDefaultResultsBeanList.add(new CityBean.ResultsBean("北京", "北京"));
        mDefaultResultsBeanList.add(new CityBean.ResultsBean("上海", "上海"));
        mDefaultResultsBeanList.add(new CityBean.ResultsBean("广州", "广州"));
        mDefaultResultsBeanList.add(new CityBean.ResultsBean("深圳", "深圳"));
        mDefaultResultsBeanList.add(new CityBean.ResultsBean("汕头", "汕头"));
        mCitySuggestionRvAdapter = new CitySuggestionRvAdapter(this);
        mCitySuggestionRvAdapter.setOnRecyclerViewListener(this);
        mResultsBeanList = new ArrayList<>();
        mResultsBeanList.addAll(mDefaultResultsBeanList);
        mCitySuggestionRvAdapter.appendData(mResultsBeanList);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mTbTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRvSearchSuggestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvSearchSuggestion.setItemAnimator(new DefaultItemAnimator());
        mRvSearchSuggestion.setAdapter(mCitySuggestionRvAdapter);
    }

    @Override
    protected void initListener() {
        mCetCity.setOnChooseEditTextListener(new OnChooseEditTextListener() {
            @Override
            public void onTextChangeed(String text) {
                mResultsBeanList.clear();
                if (text.equals("")) {
                    mResultsBeanList.addAll(mDefaultResultsBeanList);
                    mCitySuggestionRvAdapter.updataData(mResultsBeanList);
                } else {
                    getCitySuggestion(text);
                }
            }
        });
    }

    private void getCitySuggestion(String text) {
        RetrofitManager.getInstance().setBaseUrl(BASE_XIN_WEATHER_URL).create(CitySuggestionInterface.class)
                .getLocation(XIN_WEATHER_KEY, text)
                .subscribeOn(Schedulers.io())
                .map(new Func1<CityBean, List<CityBean.ResultsBean>>() {
                    @Override
                    public List<CityBean.ResultsBean> call(CityBean cityBean) {
                        return cityBean.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CityBean.ResultsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onNext(List<CityBean.ResultsBean> resultsBeen) {
                        if (resultsBeen == null || resultsBeen.size() == 0) {
                            showToast("暂无城市推荐,请输入其他城市");
                        } else {
                            mResultsBeanList = resultsBeen;
                            mCitySuggestionRvAdapter.updataData(mResultsBeanList);
                        }
                    }
                });
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (!mSubscription.isUnsubscribed()) {
//            mSubscription.unsubscribe();
//        }
    }

    @Override
    public void onItemClick(int position, View view) {
        hideInputSoft(mCetCity);
        Intent intent = new Intent();
        intent.putExtra("city_chosen", mResultsBeanList.get(position).getName());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
