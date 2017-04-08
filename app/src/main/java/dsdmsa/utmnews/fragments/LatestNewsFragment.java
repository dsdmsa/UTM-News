package dsdmsa.utmnews.fragments;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.List;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.LatestNewsFragmentVP;
import dsdmsa.utmnews.presenters.LatestNewsPresenter;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

public class LatestNewsFragment extends BaseFragment implements
        LatestNewsFragmentVP.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.NewsInteract,
        CustomTabsActivityHelper.CustomTabsFallback {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter
    LatestNewsPresenter presenter;

    private NewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager;

    CustomTabsHelperFragment customTabsHelperFragment;
    CustomTabsIntent customTabsIntent;

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
        newsAdapter = new NewsAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.loadNewsOnPage(currentPage);
            }
        });

        refreshLayout.setOnRefreshListener(this);
        presenter.loadNewsOnPage(Constants.INITIAL_PAGE);

        customTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);
        customTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.primary_dark))
                .setShowTitle(true)
                .build();


    }

    @Override
    public void addNewses(List<Post> newses) {
        newsAdapter.addNewses(newses);
    }

    @Override
    public void onRefresh() {
        newsAdapter.clearData();
        presenter.loadNewsOnPage(Constants.INITIAL_PAGE);
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
        Toast.makeText(this.getContext(), errorMsg, Toast.LENGTH_SHORT).show();
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
    public void onBookmarkClick(Post post) {
        Toast.makeText(getContext(), "bokm", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetailsClick(final Post post) {
        CustomTabsHelperFragment.open(
                getActivity(),
                customTabsIntent,
                Uri.parse(post.getLink()),
                this
        );
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
}
