package dsdmsa.utmnews.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.domain.utils.Utils;
import dsdmsa.utmnews.presentation.mvp.TagListContract;
import dsdmsa.utmnews.presentation.presenters.TagNewsListPresenter;
import dsdmsa.utmnews.presentation.views.ChromeTab;
import dsdmsa.utmnews.presentation.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.presentation.views.adapters.NewsAdapter;


public class TagNewsFragment extends BaseFragment implements
        TagListContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.Listener {

    public static final String TAG_ID = "TAG_ID";

    @InjectPresenter
    TagNewsListPresenter presenter;

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.info_msg)
    TextView infoMsg;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private NewsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private Tag tag;

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public static TagNewsFragment newInstance(Tag tag) {
        Bundle args = new Bundle();
        args.putParcelable(TAG_ID, tag);
        TagNewsFragment fragment = new TagNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = (Tag) getArguments().getParcelable(TAG_ID);
        adapter = new NewsAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.getCategoryNewses(currentPage, tag.getId());
            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        setupRecyclerView();
        presenter.refresh(tag.getId());
//        ((MainActivity) getActivity()).setToolbarTitle(tag.name);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideInfoMessage();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public String getName() {
        return tag.getName();
    }

    @Override
    public void onRefresh() {
        presenter.refresh(tag.getId());
        endlessRecyclerOnScrollListener.resetPages();
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
        if (adapter.isEmpty()) {
            infoMsg.setText(errorMsg);
            infoMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addNewses(List<SimplePost> newses) {
        adapter.addNewses(newses);
    }

    @Override
    public void refreshDatas(List<SimplePost> response) {
        adapter.clearData();
        adapter.addNewses(response);
    }

    @Override
    public void setupRecyclerView() {
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    @Override
    public void clear() {
        adapter.clearData();
    }


    @Override
    public void hideInfoMessage() {
        infoMsg.setVisibility(View.GONE);
    }

    @Override
    public void onPostClick(SimplePost post) {
        new ChromeTab(getActivity(), post.getLink());
    }

    @Override
    public void onShareClick(SimplePost post) {
        startActivity(Utils.getShareIntent(post.getLink()));
    }

    @Override
    public void onBookmark(SimplePost post) {
        presenter.bookmark(post);
    }

    @Override
    public void showBottomLoadingView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBottomLoadingView() {
        progressBar.setVisibility(View.GONE);
    }

}
