package dsdmsa.utmnews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.activityes.CategoryNewsActivity;
import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Tag;
import dsdmsa.utmnews.mvp.ClasificationVP;
import dsdmsa.utmnews.presenters.ClassificationPresenter;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.views.adapters.CategoryAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class CategoryListFragment extends BaseFragment implements
        ClasificationVP.View,
        CategoryAdapter.CategoryInteract,
        SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    ClassificationPresenter presenter;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private CategoryAdapter categoryAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryAdapter = new CategoryAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setAdapter(categoryAdapter);
        presenter.getCategoryList();
    }

    @Override
    public void showCategories(List<Category> response) {
        categoryAdapter.clearData();
        categoryAdapter.addNewses(response);
    }

    @Override
    public void showTags(List<Tag> response) {
        //....
    }

    @Override
    public void showProgressDialog() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(Integer categoryId) {
        Intent intent = new Intent(getContext(), CategoryNewsActivity.class);
        intent.putExtra(Constants.CATEGORY_ID,categoryId);
        startActivity(intent);
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getApp().getString(R.string.categories_title);
    }

    @Override
    public void retry() {

    }

    @Override
    public void onRefresh() {
        presenter.getCategoryList();
    }
}
