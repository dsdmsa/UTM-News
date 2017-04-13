package dsdmsa.utmnews.fragments;

import android.content.Context;
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
import dsdmsa.utmnews.views.ChromeTab;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.mvp.BookmarksFragmentVP;
import dsdmsa.utmnews.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class BookmarksFragment extends BaseFragment implements
        BookmarksFragmentVP.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.NewsInteract{

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter
    BookmarksFragmentPresenter presenter;

    private NewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        App.getAppComponent().inject(this);
        setupRecyclerView();
        presenter.loadNews();
        navigationPresenter.setTitle(getTitle());
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        newsAdapter = new NewsAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onRefresh() {
        newsAdapter.clearData();
        presenter.loadNews();
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
        newsAdapter.clearData();
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void addNewses(List<SimplePost> newses) {
        newsAdapter.clearData();
        newsAdapter.notifyDataSetChanged();
        newsAdapter.addNewses(newses);
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
    public void onBookmarkClick(SimplePost post) {
        presenter.removePost(post);
        presenter.loadNews();
    }

    @Override
    public void onDetailsClick( SimplePost post) {
        new ChromeTab(getActivity(), post.getLink());
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getContext().getString(R.string.bokmarks_title);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationPresenter.setTitle(getTitle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigationPresenter.setTitle(getTitle());
    }
}
