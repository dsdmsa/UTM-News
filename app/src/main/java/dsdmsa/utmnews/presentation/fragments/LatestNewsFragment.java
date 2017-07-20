package dsdmsa.utmnews.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsFragmentVP;
import dsdmsa.utmnews.presenters.NewsPresenter;
import dsdmsa.utmnews.views.ChromeTab;
import dsdmsa.utmnews.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;

import static dsdmsa.utmnews.domain.utils.Constants.INITIAL_PAGE;

public class LatestNewsFragment extends BaseFragment implements
        NewsFragmentVP.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.NewsInteract {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter(type = PresenterType.GLOBAL)
    NewsPresenter presenter;

    private NewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    public static LatestNewsFragment newInstance() {
        Bundle args = new Bundle();
        LatestNewsFragment fragment = new LatestNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                presenter.loadNewsOnPage(current_page);
            }
        };
        presenter.setupRecyclerView();
        presenter.loadNewsOnPage(INITIAL_PAGE);
    }

    @Override
    public void setupRecyclerView() {
        newsAdapter = new NewsAdapter(this);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    @Override
    public void addNewses(List<SimplePost> newses) {
        newsAdapter.addNewses(newses);
    }

    @Override
    public void refreshDatas(List<SimplePost> response) {
        setupRecyclerView();
        newsAdapter.clearData();
        newsAdapter.addNewses(response);
    }

    @Override
    public void onRefresh() {
        presenter.loadNewsOnPage(INITIAL_PAGE);
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
    public void onShareClick(String url) {
        presenter.shareString(url);
    }

    @Override
    public void onBookmarkClick(SimplePost post, int position) {
        if (post.isBookmarked()) {
            post.setBookmarked(false);
            presenter.removeBookmarkPost(post);
            newsAdapter.notifyItemChanged(position);
        } else {
            post.setBookmarked(true);
            presenter.bookmarkPost(post);
            newsAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onDetailsClick(final SimplePost post) {
        new ChromeTab(getActivity(), post.getLink());
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getApp().getString(R.string.news_lastest_title);
    }

    @Override
    public void retry() {
        presenter.loadNewsOnPage(INITIAL_PAGE);
    }

}
