package com.example.fetch_android_take_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fetch_android_take_home.databinding.ActivityMainBindingImpl;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.fetch_android_take_home.R;
import com.example.fetch_android_take_home.viewModels.MainViewModel;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ActivityMainBindingImpl binding =
                DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setMainViewModel(this.mainViewModel);

        this.mainViewModel.initActivity(this.getApplication(), this, binding.getRoot());
    }

}