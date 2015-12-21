package com.androidadvance.mvvp_weatherapp.remote;

import com.androidadvance.mvvp_weatherapp.model.ipgeolocation.IpGeolocation;
import com.androidadvance.mvvp_weatherapp.model.weather.WeatherPojo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface IpGeoLocationAPI {

    String ENDPOINT = "http://ip-api.com";

    @GET("/json") Observable<IpGeolocation> getIpGeolocation();


    class Factory {
        private static IpGeoLocationAPI instance;

        private static void create() {

            //------- enable logging ------
            OkHttpClient client = new OkHttpClient();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            client.interceptors().add(interceptor);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IpGeoLocationAPI.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            instance = retrofit.create(IpGeoLocationAPI.class);

        }
        public static synchronized IpGeoLocationAPI getApi() {
            if (instance == null) {
                create();
            }
            return instance;
        }
    }

}
