package dsdmsa.utmnews.presentation.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.presentation.mvp.HomeContract;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.views.adapters.CategoryViewPagerAdapter;
import es.dmoral.toasty.Toasty;


public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getCategories();
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
        pagerAdapter = new CategoryViewPagerAdapter(getFragmentManager(), baseFragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
