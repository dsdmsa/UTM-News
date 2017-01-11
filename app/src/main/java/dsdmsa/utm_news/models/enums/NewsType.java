package dsdmsa.utm_news.models.enums;


import dsdmsa.utm_news.fragments.LatestNewsFragment;

public enum NewsType {
    LATEST("Latest", LatestNewsFragment.newInstance()),
    TOP("Latest", LatestNewsFragment.newInstance());

    private CharSequence name;
    private LatestNewsFragment fragment;


    NewsType(String latest, LatestNewsFragment latestNewsFragment) {
        name = latest;
        fragment = latestNewsFragment;
    }

    public CharSequence getName() {
        return name;
    }

    public LatestNewsFragment getFragment() {
        return fragment;
    }
}
