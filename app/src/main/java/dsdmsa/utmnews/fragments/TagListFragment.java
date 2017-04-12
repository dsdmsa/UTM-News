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
import dsdmsa.utmnews.views.adapters.TagAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class TagListFragment extends BaseFragment
        implements ClasificationVP.View,
        TagAdapter.TagInteract {

    @InjectPresenter
    ClassificationPresenter presenter;

    private TagAdapter tagAdapter;

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
        tagAdapter = new TagAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tagAdapter);
        presenter.getTagList();
        navigationPresenter.setTitle(getTitle());
    }

    @Override
    public void showCategories(List<Category> response) {
        //....
    }

    @Override
    public void showTags(List<Tag> response) {
        tagAdapter.addNewses(response);
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
    public void onShareClick(Integer tagId) {
        navigationPresenter.addFragment(TagNewsFragment.newInstance(tagId));
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getContext().getString(R.string.tag_title);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        navigationPresenter.setTitle(getTitle());
    }

}
