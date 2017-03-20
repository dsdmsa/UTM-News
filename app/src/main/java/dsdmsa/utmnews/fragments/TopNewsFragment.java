package dsdmsa.utmnews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.presenter.GetNewsPresenter;
import dsdmsa.utmnews.fragments.presenter.NewsPresenter;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.views.NewsView;
import dsdmsa.utmnews.views.adapters.NewsAdapter;

public class TopNewsFragment extends BaseFragment  implements NewsView,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private NewsAdapter newsAdapter;
    private GetNewsPresenter presenter;

    public static TopNewsFragment newInstance() {
        Bundle args = new Bundle();
        TopNewsFragment fragment = new TopNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, rootView);
        presenter = new NewsPresenter(this);
        newsAdapter = new NewsAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);
        presenter.getNews();
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void onRefresh() {
        presenter.getNews();
    }

//    @Override
//    public void showNews(List<News> newses) {
//        newsAdapter.setNewses(newses);
//    }
//
//    @Override
//    public void addNewses(List<News> newses) {
//
//    }

    @Override
    public void showPregressDialog() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(this.getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNews(List<Post> newses) {
        newsAdapter.addNewses(newses);
    }

    @Override
    public void addNewses(List<Post> newses) {

    }
}
