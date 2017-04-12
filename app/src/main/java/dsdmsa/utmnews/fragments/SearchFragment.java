package dsdmsa.utmnews.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.mvp.NewsFragmentVP;
import dsdmsa.utmnews.presenters.NewsPresenter;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.views.MyLinearLayout;
import dsdmsa.utmnews.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class SearchFragment extends BaseFragment implements
        NewsFragmentVP.View,
        NewsAdapter.NewsInteract,
        SwipeRefreshLayout.OnRefreshListener,
        CustomTabsActivityHelper.CustomTabsFallback {

    @InjectPresenter
    NewsPresenter presenter;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private NewsAdapter newsAdapter;
    private MyLinearLayout layoutManager;

    public static SearchFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(Constants.SEARCH_KEY, searchKey);
        SearchFragment fragment = new SearchFragment();
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
        layoutManager = new MyLinearLayout(getContext());
        layoutManager.setScrollEnabled(true);
        newsAdapter = new NewsAdapter(this);
        setupRecyclerView();
        App.getAppComponent().inject(this);
        presenter.getSearchedNewses(
                getArguments().getString(Constants.SEARCH_KEY),
                Constants.ITEMS_PER_PAGE,
                Constants.INITIAL_PAGE
        );
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                layoutManager.setScrollEnabled(false);
                presenter.getSearchedNewses(
                        getArguments().getString(Constants.SEARCH_KEY),
                        Constants.ITEMS_PER_PAGE,
                        currentPage
                );
            }
        });
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        layoutManager.setScrollEnabled(true);
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
        post.setBookmarked(!post.isBookmarked());
        newsAdapter.notifyDataSetChanged();
//        repository.add(post);
    }

    @Override
    public void onDetailsClick(SimplePost post) {
//      navigationPresenter.showPostDetails(post.);
    }

    @Override
    public void openUri(Activity activity, Uri uri) {
        Toast.makeText(activity, "custom_tabs_failed", Toast.LENGTH_SHORT).show();
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, "activity_not_found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        newsAdapter.clearData();
        setupRecyclerView();
        presenter.getSearchedNewses(
                getArguments().getString(Constants.SEARCH_KEY),
                Constants.ITEMS_PER_PAGE,
                Constants.INITIAL_PAGE
        );
    }

    @Override
    public String getTitle() {
        return "S-News";
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
}
