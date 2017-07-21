package dsdmsa.utmnews.presentation.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.presentation.mvp.HomeContract;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.views.adapters.CategoryViewPagerAdapter;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;


public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;

    private CategoryViewPagerAdapter pagerAdapter;

    @InjectPresenter
    HomeFragmentPresenter presenter;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getCategories();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        Toasty.error(getContext(), errorMsg).show();
    }

    @Override
    public void displayPages(List<BaseFragment> baseFragments) {
        Timber.d(" adding fragments " + baseFragments.size());
        pagerAdapter = new CategoryViewPagerAdapter(getFragmentManager(), baseFragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setViewPager(viewPager);
        tabLayout.setDefaultTabTextColor(Color.CYAN);
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            float alp = Math.abs(0 - i);
            alp = 1 - alp / 2.5f;
            tabLayout.getTabAt(i).setAlpha(alp);
        }
            tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pagerAdapter.getCount(); i++) {
                    float alp = Math.abs(position - i);
                    alp = 1 - alp / 2.5f;
                    tabLayout.getTabAt(i).setAlpha(alp);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        tabLayout.setPivotX(0.15f);

//        tabLayout.setDefaultTabTextColor(new ColorStateList());
    }

}
