package dsdmsa.utmnews.activityes.categories;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.activityes.BaseActivity;
import dsdmsa.utmnews.fragments.presenter.CategoryPresenter;
import dsdmsa.utmnews.fragments.presenter.GetCategoryPresenter;
import dsdmsa.utmnews.views.CategoryView;

public class CategoriesActivity extends BaseActivity implements CategoryView,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private GetCategoryPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new CategoryPresenter(this);
        presenter.getcategorys();
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        presenter.getcategorys();
    }

    @Override
    public void showPregressDialog() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void showCategories(List<Category> categories) {
//
//    }
}
