package dsdmsa.utmnews.activityes;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static dsdmsa.utmnews.App.appComponent;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent.inject(this);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
