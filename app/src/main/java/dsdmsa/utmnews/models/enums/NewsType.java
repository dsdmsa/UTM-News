package dsdmsa.utmnews.models.enums;


import android.support.v4.app.Fragment;

import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.fragments.TopNewsFragment;

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
