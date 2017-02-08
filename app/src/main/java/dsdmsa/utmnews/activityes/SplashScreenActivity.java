package dsdmsa.utmnews.activityes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dsdmsa.utmnews.utils.Navigator;

import static dsdmsa.utmnews.This.appComponent;

public class SplashScreenActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent.inject(this);
        navigator.startMainActivity();
        finish();
    }
}
