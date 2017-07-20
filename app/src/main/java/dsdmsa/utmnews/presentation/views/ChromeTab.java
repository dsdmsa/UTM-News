package dsdmsa.utmnews.presentation.views;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import dsdmsa.utmnews.R;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;


public class ChromeTab implements CustomTabsActivityHelper.CustomTabsFallback {

    private CustomTabsIntent customTabsIntent;

    public ChromeTab(Activity context,String url) {

        customTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(context, R.color.primary_dark))
                .setShowTitle(true)
                .build();

        CustomTabsHelperFragment.open(
                context,
                customTabsIntent,
                Uri.parse(url),
                this
        );

    }

    @Override
    public void openUri(Activity activity, Uri uri) {
        //        getViewState().showInfoMessage("error");
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
//            getViewState().showInfoMessage("activity not found");
        }
    }
}
