package com.androidadvance.mvvp_weatherapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidadvance.mvvp_weatherapp.databinding.ActivityMainBinding;
import com.androidadvance.mvvp_weatherapp.model.weather.WeatherPojo;
import com.androidadvance.mvvp_weatherapp.viewmodel.MainViewModel;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, this);
        binding.setViewModel(mainViewModel);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    @Override public void onDataChanged(WeatherPojo weatherPojo) {
        Timber.d("onDataChanged");
    }
}
