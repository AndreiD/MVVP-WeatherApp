package com.androidadvance.mvvp_weatherapp.remote;

import com.androidadvance.mvvp_weatherapp.model.weather.WeatherPojo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherAPI {

    String ENDPOINT = "http://api.openweathermap.org";

    @GET("/data/2.5/weather?appid=2de143494c0b295cca9337e1e96b00e0") Observable<WeatherPojo> getWeatherForLatLon(@Query("lat") double lat, @Query("lng") double lng, @Query("units") String units);

    @GET("/data/2.5/weather?appid=2de143494c0b295cca9337e1e96b00e0") Observable<WeatherPojo> getWeatherForCity(@Query("q") String city, @Query("units") String units);




    class Factory {
        private static WeatherAPI instance;

        private static void create() {

            //------- enable logging ------
            OkHttpClient client = new OkHttpClient();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            client.interceptors().add(interceptor);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WeatherAPI.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            instance = retrofit.create(WeatherAPI.class);

        }
        public static synchronized WeatherAPI getApi() {
            if (instance == null) {
                create();
            }
            return instance;
        }
    }

}
