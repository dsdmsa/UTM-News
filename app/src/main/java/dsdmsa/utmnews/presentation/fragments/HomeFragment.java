package dsdmsa.utmnews.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.presentation.mvp.HomeContract;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.views.adapters.CategoryViewPagerAdapter;


public class HomeFragment extends BaseFragment implements
        HomeContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;

    @InjectPresenter
    HomeFragmentPresenter presenter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private CategoryViewPagerAdapter pagerAdapter;

    @BindView(R.id.tv_error)
    TextView errorTextView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public String getName() {
        return "HomeFragment";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setPageMargin(15);
        viewPager.setClipToPadding(false);
        presenter.getCategories();
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setTabAlpha(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void showProgressDialog() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        errorTextView.setText(errorMsg);
    }

    @Override
    public void onRefresh() {
        presenter.getCategories();
    }

    @Override
    public void updateCategories(List<Category> categories) {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new AllNewsFragment());
        for (Category category : categories) {
            fragments.add(CategoryNewsFragment.newInstance(category));
        }
        pagerAdapter = new CategoryViewPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);
        setTabAlpha(0);
    }

    @Override
    public void hideRefreshLaout() {
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshLayout() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    private void setTabAlpha(int position) {
        if (pagerAdapter != null)
            for (int i = 0; i < pagerAdapter.getCount(); i++) {
                float alp = Math.abs(position - i);
                alp = 1 - alp / 2.5f;
                if (alp < 0f)
                    alp = 0.1f;
                tabLayout.getTabAt(i).setAlpha(alp);
            }
    }

}
