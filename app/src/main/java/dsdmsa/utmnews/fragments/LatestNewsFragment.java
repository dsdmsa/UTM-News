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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.LatestNewsFragmentVP;
import dsdmsa.utmnews.presenters.LatestNewsPresenter;
import dsdmsa.utmnews.repository.PostRepository;
import dsdmsa.utmnews.views.MyLinearLayout;
import dsdmsa.utmnews.views.adapters.EndlessRecyclerOnScrollListener;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

import static dsdmsa.utmnews.utils.Constants.INITIAL_PAGE;
import static dsdmsa.utmnews.utils.Constants.TEXT_PLAIN;

public class LatestNewsFragment extends BaseFragment implements
        LatestNewsFragmentVP.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.NewsInteract,
        CustomTabsActivityHelper.CustomTabsFallback {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter(type = PresenterType.GLOBAL)
    LatestNewsPresenter presenter;

    @Inject
    PostRepository repository;

    private NewsAdapter newsAdapter;
    private MyLinearLayout layoutManager;

    private CustomTabsHelperFragment customTabsHelperFragment;
    private CustomTabsIntent customTabsIntent;

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
        App.getAppComponent().inject(this);
        setupRecyclerView();

        refreshLayout.setOnRefreshListener(this);
        presenter.loadNewsOnPage(INITIAL_PAGE);

        customTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);
        customTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.primary_dark))
                .setShowTitle(true)
                .build();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                layoutManager.setScrollEnabled(false);
                presenter.loadNewsOnPage(currentPage);
            }
        });
    }

    @Override
    public void addNewses(List<Post> newses) {
        newsAdapter.addNewses(newses);
        layoutManager.setScrollEnabled(true);
    }

    @Override
    public void onRefresh() {
        newsAdapter.clearData();
        setupRecyclerView();
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
        layoutManager.setScrollEnabled(true);
    }

    @Override
    public void onShareClick(String url) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType(TEXT_PLAIN);
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)));
    }

    @Override
    public void onBookmarkClick(Post post) {
        post.setBookmarked(!post.isBookmarked());
        newsAdapter.notifyDataSetChanged();
        repository.add(post);
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
