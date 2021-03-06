package dsdmsa.utmnews.presentation.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import dsdmsa.utmnews.presentation.fragments.BaseFragment;


public class CategoryViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments = new ArrayList<>();

    public CategoryViewPagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments) {
        super(fm);
        fragments.clear();
        fragments.addAll(baseFragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }

    public void clear() {
        fragments.clear();
        notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
