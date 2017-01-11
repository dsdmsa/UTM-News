package dsdmsa.utm_news.utils;


import android.content.Context;
import android.content.Intent;

import dsdmsa.utm_news.activityes.main.MainActivity;

public class Navigator {
    private Context mContext;

    public Navigator(Context context) {
        mContext = context;
    }

    public void startMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
