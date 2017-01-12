package dsdmsa.utm_news.models.enums;


import android.support.v4.app.Fragment;

import dsdmsa.utm_news.fragments.LatestNewsFragment;
import dsdmsa.utm_news.fragments.TopNewsFragment;

public enum NewsType {
    LATEST("Latest", LatestNewsFragment.newInstance()),
    TOP("Top", TopNewsFragment.newInstance());

    private CharSequence name;
    private Fragment fragment;


    NewsType(String latest, Fragment latestNewsFragment) {
        name = latest;
        fragment = latestNewsFragment;
    }

    public CharSequence getName() {
        return name;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
