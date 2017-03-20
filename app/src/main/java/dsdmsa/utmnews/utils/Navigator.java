package dsdmsa.utmnews.utils;


import android.content.Context;
import android.content.Intent;

import dsdmsa.utmnews.activityes.PrefActivity;
import dsdmsa.utmnews.activityes.about.AboutActivity;
import dsdmsa.utmnews.activityes.categories.CategoriesActivity;
import dsdmsa.utmnews.activityes.main.MainActivity;
import dsdmsa.utmnews.activityes.news.NewsActivity;
import dsdmsa.utmnews.activityes.search.SearchActivity;
import dsdmsa.utmnews.models.Post;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Navigator {
    private Context mContext;

    public Navigator(Context context) {
        mContext = context;
    }

    private Intent getIntent(Class aClass) {
        Intent intent = new Intent(mContext, aClass);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


    public void startMainActivity() {
        mContext.startActivity(getIntent(MainActivity.class));
    }

    public void startCategoryActivity() {
        mContext.startActivity(getIntent(CategoriesActivity.class));
    }

    public void startSearchActivity() {
        mContext.startActivity(getIntent(SearchActivity.class));
    }

    public void startAbautActivity() {
        mContext.startActivity(getIntent(AboutActivity.class));
    }

    public void startCollectionsBookmark() {

    }

    public void startSettings() {
        mContext.startActivity(getIntent(PrefActivity.class));
    }

    public void startFeedBack() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "utm-news feedback");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"urmanschi.mihail@ati.utm.md"});
        Intent mailer = Intent.createChooser(intent, null);
        mailer.addFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mailer);
    }

    public void startNewsActivity(Post post) {
        Intent intent = getIntent(NewsActivity.class);
        intent.putExtra("POST", post.getContent().rendered );
        mContext.startActivity(intent);
    }
}
