package dsdmsa.utmnews.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.activityes.TagActivity;
import dsdmsa.utmnews.presentation.mvp.TagContract;
import dsdmsa.utmnews.presentation.presenters.TagPresenter;
import dsdmsa.utmnews.presentation.views.adapters.TagAdapter;
import es.dmoral.toasty.Toasty;


public class TagListFragment extends BaseFragment implements
        TagContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        TagAdapter.TagInteract {

    @InjectPresenter
    TagPresenter presenter;

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private TagAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TagAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
        presenter.getTags();
    }

    @Override
    public String getName() {
        return "TagListFragment";
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void showProgressDialog() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        Toasty.error(getContext(), errorMsg).show();
    }

    @Override
    public void showTags(final List<Tag> response) {
        getActivity().runOnUiThread(() -> adapter.addTags(response));
    }

    @Override
    public void clear() {
        adapter.clearData();
    }

    @Override
    public void onTagClicked(Tag tag) {
        startActivity(TagActivity.getTagIntent(getContext(), tag));
    }
}
