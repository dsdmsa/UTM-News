package dsdmsa.utmnews.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.presentation.mvp.HomeContract;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.views.adapters.CategoryViewPagerAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class HomeFragment extends BaseFragment implements
        HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getCategories();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setPageMargin(20);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(80, 0, 80, 0);
        swipeRefreshLayout.setOnRefreshListener(this);
        viewPager.requestTransparentRegion(viewPager);
    }

    @Override
    public void showProgressDialog() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        getActivity().runOnUiThread(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        errorTextView.setText(errorMsg);
    }

    @Override
    public void displayPages(List<BaseFragment> baseFragments) {
        if (!baseFragments.isEmpty()) {
            swipeRefreshLayout.setVisibility(View.GONE);
        }

        viewPager.setAdapter(null);

        Single.fromCallable(() -> new CategoryViewPagerAdapter(getFragmentManager(), baseFragments))
                .observeOn(AndroidSchedulers.mainThread())
                .map(setViewPagerAdapter())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter -> {
                    if (pagerAdapter != null) {
                        pagerAdapter.clear();
                    }
                    pagerAdapter = adapter;
                    setTabAlpha(0);
                    Timber.d("added fragments ot pager finished");
                });

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

    @NonNull
    private Function<CategoryViewPagerAdapter, CategoryViewPagerAdapter> setViewPagerAdapter() {
        return categoryViewPagerAdapter -> {
            viewPager.setAdapter(categoryViewPagerAdapter);
            tabLayout.setViewPager(viewPager);
            return categoryViewPagerAdapter;
        };
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

    @Override
    public void onRefresh() {
        presenter.getCategories();
    }
}
