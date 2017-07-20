package dsdmsa.utmnews.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsFragmentVP;
import dsdmsa.utmnews.presenters.NewsPresenter;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.views.ChromeTab;
import dsdmsa.utmnews.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class TagNewsFragment extends BaseFragment implements
        NewsFragmentVP.View,
        NewsAdapter.NewsInteract,
        SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    NewsPresenter presenter;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private NewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    public static TagNewsFragment newInstance(int tagId) {
        Bundle args = new Bundle();
        args.putInt(Constants.TAG_ID, tagId);
        TagNewsFragment fragment = new TagNewsFragment();
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
        refreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getContext());
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.getNewsByTag(
                        getArguments().getInt(Constants.TAG_ID),
                        Constants.ITEMS_PER_PAGE,
                        currentPage
                );
            }
        };
        newsAdapter = new NewsAdapter(this);
        setupRecyclerView();
        presenter.getNewsByTag(
                getArguments().getInt(Constants.TAG_ID),
                Constants.ITEMS_PER_PAGE,
                Constants.INITIAL_PAGE
        );
    }

    @Override
    public  void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType(Constants.TEXT_PLAIN);
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)));
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
    public void onDetailsClick(SimplePost post) {
        new ChromeTab(getActivity(), post.getLink());
    }

    @Override
    public void onRefresh() {
        presenter.getNewsByTag(
                getArguments().getInt(Constants.TAG_ID),
                Constants.ITEMS_PER_PAGE,
                Constants.INITIAL_PAGE
        );
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getApp().getString(R.string.tag_sewses_title);
    }

    @Override
    public void retry() {
        presenter.getNewsByTag(
                getArguments().getInt(Constants.TAG_ID),
                Constants.ITEMS_PER_PAGE,
                Constants.INITIAL_PAGE
        );
    }

    @Override
    public void addNewses(List<SimplePost> newses) {
        newsAdapter.addNewses(newses);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshDatas(List<SimplePost> response) {
        setupRecyclerView();
        newsAdapter.clearData();
        newsAdapter.addNewses(response);
    }
}
