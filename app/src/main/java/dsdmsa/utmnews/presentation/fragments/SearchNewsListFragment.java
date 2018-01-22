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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Utils;
import dsdmsa.utmnews.presentation.activityes.MainActivity;
import dsdmsa.utmnews.presentation.mvp.SearchNewsContract;
import dsdmsa.utmnews.presentation.presenters.SearchNewsListPresenter;
import dsdmsa.utmnews.presentation.views.ChromeTab;
import dsdmsa.utmnews.presentation.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.presentation.views.adapters.NewsAdapter;

public class SearchNewsListFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        SearchNewsContract.View,
        NewsAdapter.Listener {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.info_msg)
    TextView infoMsg;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @InjectPresenter
    SearchNewsListPresenter presenter;

    private NewsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    private String searchTerm = "";

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public String getName() {
        return "SearchNewsListFragment";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.getNews(currentPage, searchTerm);
            }

            @Override
            public void isScrolling() {
                ((MainActivity) getActivity()).onSearchClicked();
            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    @Override
    public void onRefresh() {
        endlessRecyclerOnScrollListener.resetPages();
        presenter.refreshNewses(searchTerm);
    }

    @Override
    public void showProgressDialog() {
        getActivity().runOnUiThread(() -> swipeRefresh.setRefreshing(true));
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
    public void clearList() {
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

    @Subscribe
    public void search(String key) {
        searchTerm = key;
        presenter.refreshNewses(key);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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
