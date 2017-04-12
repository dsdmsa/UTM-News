package dsdmsa.utmnews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Tag;
import dsdmsa.utmnews.mvp.ClasificationVP;
import dsdmsa.utmnews.presenters.ClassificationPresenter;
import dsdmsa.utmnews.views.adapters.CategoryAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class CategoryListFragment extends BaseFragment
        implements ClasificationVP.View,
        CategoryAdapter.CategoryInteract {

    @InjectPresenter
    ClassificationPresenter presenter;

    private CategoryAdapter categoryAdapter;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryAdapter = new CategoryAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(categoryAdapter);
        presenter.getCategoryList();
    }

    @Override
    public void showCategories(List<Category> response) {
        categoryAdapter.addNewses(response);
    }

    @Override
    public void showTags(List<Tag> response) {
        //....
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
        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareClick(Integer categoryId) {
        navigationPresenter.addFragment(CategoryNewsFragment.newInstance(categoryId));
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getContext().getString(R.string.categories_title);
    }


    @Override
    public void onResume() {
        super.onResume();
        navigationPresenter.setTitle(getTitle());
    }
}
