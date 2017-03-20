package dsdmsa.utmnews.activityes.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.activityes.BaseActivity;
import dsdmsa.utmnews.fragments.presenter.GetNewsPresenter;
import dsdmsa.utmnews.fragments.presenter.NewsPresenter;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.views.NewsView;

public class NewsListActivity extends BaseActivity implements NewsView,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;


    private GetNewsPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new NewsPresenter(this);

        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showNews(List<Post> newses) {

    }

    @Override
    public void addNewses(List<Post> newses) {

    }

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
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.getNews();
    }
}
