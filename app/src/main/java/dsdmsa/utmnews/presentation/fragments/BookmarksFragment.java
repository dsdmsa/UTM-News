package dsdmsa.utmnews.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Utils;
import dsdmsa.utmnews.presentation.mvp.BookmarsContract;
import dsdmsa.utmnews.presentation.presenters.BookmarksPresenter;
import dsdmsa.utmnews.presentation.views.ChromeTab;
import dsdmsa.utmnews.presentation.views.adapters.NewsAdapter;

public class BookmarksFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BookmarsContract.View,
        NewsAdapter.Listener {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @InjectPresenter
    BookmarksPresenter presenter;

    @BindView(R.id.info_msg)
    TextView infoMsg;

    private NewsAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public String getName() {
        return "Bookmarks";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        presenter.refreshNewses();
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
        infoMsg.setVisibility(View.VISIBLE);
        infoMsg.setText(errorMsg);
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

}
