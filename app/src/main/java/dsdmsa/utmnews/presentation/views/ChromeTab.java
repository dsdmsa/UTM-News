package dsdmsa.utmnews.presentation.views;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import dsdmsa.utmnews.R;

public class  ChromeTab {

    public ChromeTab(Activity context, String url) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.primary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.primary_dark));
        CustomTabsIntent customTabsIntent = intentBuilder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }
}
