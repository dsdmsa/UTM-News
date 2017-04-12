package dsdmsa.utmnews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.mvp.LatestNewsFragmentVP;
import dsdmsa.utmnews.presenters.LatestNewsPresenter;
import dsdmsa.utmnews.views.MyLinearLayout;
import dsdmsa.utmnews.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;

import static dsdmsa.utmnews.utils.Constants.INITIAL_PAGE;

public class LatestNewsFragment extends BaseFragment implements
        LatestNewsFragmentVP.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.NewsInteract {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter(type = PresenterType.GLOBAL)
    LatestNewsPresenter presenter;

    private NewsAdapter newsAdapter;
    private MyLinearLayout layoutManager;
//    private Handler handler = new Handler();
//    private boolean isRefreshing = false;

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
        layoutManager = new MyLinearLayout(getContext());
        newsAdapter = new NewsAdapter(this);
        setupRecyclerView();
        refreshLayout.setOnRefreshListener(this);
        presenter.loadNewsOnPage(INITIAL_PAGE);
//        handler.post(refreshing);
    }

    private void setupRecyclerView() {
        layoutManager.setScrollEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.loadNewsOnPage(currentPage);
            }
        });
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

//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//            }
//        });

//        isRefreshing = true;
        refreshLayout.setRefreshing(true);
    }

//    private final Runnable refreshing = new Runnable() {
//        public void run() {
//            try {
//            /* TODO : isRefreshing should be attached to your data request status */
//                if (refreshLayout.isRefreshing()) {
//                    // re run the verification after 1 second
//                    handler.postDelayed(this, 1000);
//
//                } else {
//                    // stop the animation after the data is fully loaded
//                    refreshLayout.setRefreshing(false);
//                    // TODO : update your list with the new data
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };

    @Override
    public void hideProgressDialog() {
//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(false);
//            }
//        });
//        isRefreshing = false;
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        layoutManager.setScrollEnabled(true);
    }

    @Override
    public void onShareClick(String url) {
        presenter.shareString(url);
    }

    @Override
    public void onBookmarkClick(SimplePost post) {
        presenter.bookmarkPost(post);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetailsClick(final SimplePost post) {
//        navigationPresenter.showPostDetails(post);
    }

    @Override
    public String getTitle() {
        return "UTM-News";
    }

}
