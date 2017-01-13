package dsdmsa.utm_news.utils;


import android.content.Context;
import android.content.Intent;

import dsdmsa.utm_news.activityes.SearchActivity;
import dsdmsa.utm_news.activityes.categories.CategoriesActivity;
import dsdmsa.utm_news.activityes.main.MainActivity;

public class Navigator {
    private Context mContext;

    private Intent getIntent(Class aClass) {
        Intent intent = new Intent(mContext, aClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public Navigator(Context context) {
        mContext = context;
    }

    public void startMainActivity() {
        mContext.startActivity(getIntent(MainActivity.class));
    }

    public void startCategoryActivity() {
        mContext.startActivity(getIntent(CategoriesActivity.class));
    }

    public void openSearchActivity() {
        mContext.startActivity(getIntent(SearchActivity.class));
    }

}
