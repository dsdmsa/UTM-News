package dsdmsa.utmnews.presentation.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.CategoryInteractor;
import dsdmsa.utmnews.data.interactor.TagInteractor;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Tag;

public class SplashScreenActivity extends AppCompatActivity implements CategoryInteractor.Callback, TagInteractor.Callback {

    @Inject
    CategoryInteractor categoryInteractor;

    @Inject
    TagInteractor tagInteractor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        categoryInteractor.getCategories(this);
        tagInteractor.getTags(this);

    }

    @Override
    public void onCategoryLoaded(List<Category> tagList) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onTagLoaded(List<Tag> tagList) {

    }

    @Override
    public void onTagNewsLoaded() {

    }

    @Override
    public void onError(String errorMsg) {

    }
}
