package dsdmsa.utmnews.presentation.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.CategoryInteractor;
import dsdmsa.utmnews.data.interactor.TagInteractor;

public class SplashScreenActivity extends AppCompatActivity  {

    @Inject
    CategoryInteractor categoryInteractor;

    @Inject
    TagInteractor tagInteractor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        categoryInteractor.getCategories().subscribe(
                categories -> {},
                error->{}
        );
        tagInteractor.getTags().subscribe(
                tags ->{},
                error ->{}
        );
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
