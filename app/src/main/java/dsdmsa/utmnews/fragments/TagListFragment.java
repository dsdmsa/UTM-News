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
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Tag;
import dsdmsa.utmnews.mvp.TagsFragmentVP;
import dsdmsa.utmnews.presenters.TagFragmentPresenter;
import dsdmsa.utmnews.views.adapters.TagAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class TagListFragment extends BaseFragment
        implements TagsFragmentVP.View,
        TagAdapter.TagInteract {

    @InjectPresenter
    TagFragmentPresenter presenter;

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

}
