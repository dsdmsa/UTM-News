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

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.mvp.BookmarksFragmentVP;
import dsdmsa.utmnews.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.repository.PostRepository;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.views.MyLinearLayout;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import es.dmoral.toasty.Toasty;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class BookmarksFragment extends BaseFragment implements
        BookmarksFragmentVP.View,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.NewsInteract,
        CustomTabsActivityHelper.CustomTabsFallback {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @InjectPresenter
    BookmarksFragmentPresenter presenter;

    @Inject
    PostRepository repository;

    private NewsAdapter newsAdapter;
    private MyLinearLayout layoutManager;
    private CustomTabsHelperFragment customTabsHelperFragment;
    private CustomTabsIntent customTabsIntent;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        App.getAppComponent().inject(this);
        customTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);
        customTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(getContext(), R.color.primary_dark))
                .setShowTitle(true)
                .build();
        setupRecyclerView();
        presenter.loadNews();

    }

    private void setupRecyclerView() {
        layoutManager = new MyLinearLayout(getContext());
        layoutManager.setScrollEnabled(true);
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
    }

    @Override
    public void addNewses(List<SimplePost> newses) {
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
        post.setBookmarked(!post.isBookmarked());
//        repository.delete(post);
        newsAdapter.removeItem(post);
    }

    @Override
    public void onDetailsClick(final SimplePost post) {
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

    @Override
    public String getTitle() {
        return App.getAppComponent().getContext().getString(R.string.bokmarks_title);
    }
}
