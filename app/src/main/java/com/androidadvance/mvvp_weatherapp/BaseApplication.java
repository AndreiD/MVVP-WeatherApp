package com.androidadvance.mvvp_weatherapp;

import android.app.Application;
import android.content.Context;

import com.androidadvance.mvvp_weatherapp.remote.IpGeoLocationAPI;
import com.androidadvance.mvvp_weatherapp.remote.WeatherAPI;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BaseApplication extends Application {

    private Scheduler defaultSubscribeScheduler;
    private WeatherAPI weatherApi;
    private IpGeoLocationAPI ipGeoLocationAPI;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }


    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public IpGeoLocationAPI getIpGeoLocationApi() {
        if (ipGeoLocationAPI == null) {
            ipGeoLocationAPI = IpGeoLocationAPI.Factory.getApi();
        }
        return ipGeoLocationAPI;
    }

    public WeatherAPI getWeatherApi() {
        if (weatherApi == null) {
            weatherApi = WeatherAPI.Factory.getApi();
        }
        return weatherApi;
    }

    //---------- Testing ----------
    //For setting mocks during testing
    public void setWeatherApi(WeatherAPI weatherApi) {
        this.weatherApi = weatherApi;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}