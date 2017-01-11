package dsdmsa.utm_news.fragments;


import android.os.Bundle;

import dsdmsa.utm_news.R;

public class LatestNewsFragment extends BaseFragment {
    public static LatestNewsFragment newInstance() {
        Bundle args = new Bundle();
        LatestNewsFragment fragment = new LatestNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }
}
