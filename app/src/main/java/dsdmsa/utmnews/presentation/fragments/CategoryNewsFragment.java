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
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.presentation.mvp.CategoryContract;
import dsdmsa.utmnews.presentation.presenters.CategoryNewsListPresenter;
import dsdmsa.utmnews.presentation.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.presentation.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;


public class CategoryNewsFragment extends BaseFragment implements
        CategoryContract.View,
        SwipeRefreshLayout.OnRefreshListener, NewsAdapter.Listener {

    @InjectPresenter
    CategoryNewsListPresenter presenter;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private NewsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public static CategoryNewsFragment newInstance(Category categoryId) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.CATEGORY_ID, categoryId);
        CategoryNewsFragment fragment = new CategoryNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = (Category) getArguments().getParcelable(Constants.CATEGORY_ID);
        presenter.setCategory(category);

        adapter = new NewsAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        setupRecyclerView();
        presenter.refresh();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public String getName() {
        return category.getName();
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
        recycleView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                presenter.getCategoryNewses(current_page);
            }
        });
    }

    @Override
    public void onPostClick(SimplePost post) {

    }
}
