package com.rdc.project.weatherforecast.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rdc.project.weatherforecast.app.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rdc.project.weatherforecast.constant.Constant.BASE_HE_WEATHER_URL;

public class RetrofitManager {

    private static RetrofitManager sInstance;
    private static Retrofit sRetrofit;
    private static Gson sGson;
    private static String cookie = "";

    public RetrofitManager(String baseUrl) {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static void reset() {
        sInstance = null;
    }

    public static RetrofitManager getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                sInstance = new RetrofitManager(BASE_HE_WEATHER_URL);
            }
        }
        return sInstance;
    }

    public RetrofitManager setBaseUrl(String baseUrl) {
        sInstance = new RetrofitManager(baseUrl);
        return sInstance;
    }

    public <T> T create(Class<T> service) {
        return sRetrofit.create(service);
    }

    private static OkHttpClient httpClient() {
        File cacheFile = new File(FileUtil.getAppCacheDir(App.getContext()), "/HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkReachable(App.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtil.isNetworkReachable(App.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };
        return new OkHttpClient.Builder()
                .cache(cache).addInterceptor(cacheInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static Gson gson() {
        if (sGson == null) {
            synchronized (RetrofitManager.class) {
                sGson = new GsonBuilder().setLenient().create();
            }
        }
        return sGson;
    }
}
