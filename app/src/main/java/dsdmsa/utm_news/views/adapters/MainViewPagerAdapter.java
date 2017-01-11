package dsdmsa.utm_news.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dsdmsa.utm_news.models.enums.NewsType;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public MainViewPagerAdapter(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
       return NewsType.values()[position].getFragment();
    }

    @Override
    public int getCount() {
        return NewsType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NewsType.values()[position].getName();
    }
}