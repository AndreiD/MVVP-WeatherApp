package com.androidadvance.mvvp_weatherapp.viewmodel;


import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.androidadvance.mvvp_weatherapp.BaseApplication;
import com.androidadvance.mvvp_weatherapp.R;
import com.androidadvance.mvvp_weatherapp.model.weather.WeatherPojo;
import com.androidadvance.mvvp_weatherapp.remote.IpGeoLocationAPI;
import com.androidadvance.mvvp_weatherapp.remote.WeatherAPI;
import com.androidadvance.mvvp_weatherapp.util.UnitLocale;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * View model for the MainActivity
 */
public class MainViewModel implements ViewModel {

    public ObservableField<String> city;
    public ObservableField<String> temp_current;
    public ObservableField<String> temp_min_max;
    public ObservableField<String> conditions;
    public ObservableField<String> humidity;
    public ObservableField<String> pressure;
    public ObservableField<String> wind;
    public ObservableField<Drawable> weather_icon;

    //---- visibility ----
    public ObservableField<Integer> viewsVisibility;
    public ObservableField<Integer> progressVisibility;


    private Context context;
    private Subscription subscription;
    private DataListener dataListener;
    private WeatherPojo weatherPojo;


    public MainViewModel(Context context, DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;


        city = new ObservableField<>(context.getString(R.string.default_city));
        temp_current = new ObservableField<>(context.getString(R.string.temp_current));
        temp_min_max = new ObservableField<>(context.getString(R.string.temp_min_max));
        conditions = new ObservableField<>(context.getString(R.string.conditions));
        humidity = new ObservableField<>(context.getString(R.string.humidity));
        pressure = new ObservableField<>(context.getString(R.string.pressure));
        wind = new ObservableField<>(context.getString(R.string.wind));
        weather_icon = new ObservableField<>(context.getResources().getDrawable(R.drawable.ic_rain));

        viewsVisibility = new ObservableField<>(View.GONE);
        progressVisibility = new ObservableField<>(View.VISIBLE);


        loadWeather();
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        subscription = null;
        context = null;
        dataListener = null;
    }

    public void onClickFloatIcon(View view) {

        viewsVisibility.set(View.GONE);
        progressVisibility.set(View.VISIBLE);

        loadWeather();
    }

    private void loadWeather() {

        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();

        BaseApplication application = BaseApplication.get(context);

        IpGeoLocationAPI ipGeoLocationApi = application.getIpGeoLocationApi();
        WeatherAPI weatherApi = application.getWeatherApi();

        subscription = ipGeoLocationApi.getIpGeolocation()
                .concatMap(ipGeolocation -> Observable.just(ipGeolocation))
                .concatMap(ipGeolocation1 -> Observable.defer(() -> weatherApi.getWeatherForCity(ipGeolocation1.getCity() + "," + ipGeolocation1.getCountry(), UnitLocale.getDefault())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeatherPojo>() {
                    @Override public void onCompleted() {
                        if (dataListener != null) dataListener.onDataChanged(weatherPojo);
                    }

                    @Override public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override public void onNext(WeatherPojo weatherPojo) {
                        MainViewModel.this.weatherPojo = weatherPojo;

                        city.set(weatherPojo.getName());
                        temp_current.set(weatherPojo.getMain().getTemp() + context.getResources().getString(R.string.degrees_symbol));
                        temp_min_max.set(String.valueOf(weatherPojo.getMain().getTempMin()) + context.getResources().getString(R.string.degrees_symbol) + " " + String.valueOf(weatherPojo.getMain().getTempMax()) + context.getResources().getString(R.string.degrees_symbol));
                        conditions.set(weatherPojo.getWeather().get(0).getDescription());
                        humidity.set(context.getString(R.string.humidity) + " " + weatherPojo.getMain().getHumidity() + "%");

                        String wind_suffix = context.getResources().getString(R.string.wind_suffix_metric);
                        if (UnitLocale.getDefault().equals(UnitLocale.Imperial)) wind_suffix = context.getResources().getString(R.string.wind_suffix_imperial);
                        wind.set(context.getString(R.string.wind) + " " + String.valueOf(weatherPojo.getWind().getSpeed()) + wind_suffix);

                        pressure.set(context.getString(R.string.pressure) + " " + weatherPojo.getMain().getPressure() + "hPa");
                        weather_icon.set(context.getResources().getDrawable(getIcon(weatherPojo.getWeather().get(0).getId())));

                        viewsVisibility.set(View.VISIBLE);
                        progressVisibility.set(View.GONE);

                    }
                });


    }

    //http://openweathermap.org/weather-conditions
    public static int getIcon(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_thunderstorm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_thunderstorm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return -1;
    }


    public interface DataListener {
        void onDataChanged(WeatherPojo weatherPojo);
    }


}