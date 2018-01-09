package com.rdc.project.weatherforecast.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rdc.project.weatherforecast.MainActivity;
import com.rdc.project.weatherforecast.bean.LocationBean;
import com.rdc.project.weatherforecast.service.LocationInterface;
import com.rdc.project.weatherforecast.utils.RetrofitManager;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.rdc.project.weatherforecast.constant.Constant.BASE_GAODE_LOCATE_URL;
import static com.rdc.project.weatherforecast.constant.Constant.GAODE_KEY;

public class SplashActivity extends AppCompatActivity {

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = RetrofitManager.getInstance().setBaseUrl(BASE_GAODE_LOCATE_URL)
                .create(LocationInterface.class)
                .getLocation(GAODE_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LocationBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onNext(LocationBean locationBean) {
                        String city = locationBean.getCity().replace("市", "");
                        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("location", city);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
