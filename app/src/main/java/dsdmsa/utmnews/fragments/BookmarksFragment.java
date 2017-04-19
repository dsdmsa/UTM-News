package dsdmsa.utmnews.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.mvp.BookmarksFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.repository.GetAllRealmPostsSpecification;
import dsdmsa.utmnews.repository.PostRepository;
import dsdmsa.utmnews.utils.Constants;
import dsdmsa.utmnews.views.ChromeTab;
import dsdmsa.utmnews.views.adapters.BookmarkNewsAdapter;
import es.dmoral.toasty.Toasty;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by dsdmsa on 4/8/17.
 */

public class BookmarksFragment extends BaseFragment implements
        BookmarksFragmentVP.View,
        BookmarkNewsAdapter.NewsInteract,
        OnDataLoaded<RealmResults<SimplePost>> {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @InjectPresenter
    BookmarksFragmentPresenter presenter;

    @Inject
    PostRepository repository;

    private BookmarkNewsAdapter newsAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App.getAppComponent().inject(this);
        navigationPresenter.setTitle(getTitle());
        repository.querry(new GetAllRealmPostsSpecification(this));
    }

    @Override
    public void showInfoMessage(String errorMsg) {
        Toasty.info(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
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
    public void onDetailsClick(SimplePost post) {
        new ChromeTab(getActivity(), post.getLink());
    }

    @Override
    public String getTitle() {
        return App.getAppComponent().getApp().getString(R.string.bokmarks_title);
    }

    @Override
    public void retry() {

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

    @Override
    public void onSuccess(RealmResults<SimplePost> response) {
        Timber.d(" result size " + response.size());
        layoutManager = new LinearLayoutManager(getContext());
        newsAdapter = new BookmarkNewsAdapter(getContext(), response, true, true, null, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onError(String errorMsg) {
        showInfoMessage(errorMsg);
    }

}
