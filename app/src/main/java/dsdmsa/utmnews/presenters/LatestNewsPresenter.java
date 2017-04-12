package dsdmsa.utmnews.presenters;


import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.LatestNewsFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;
import dsdmsa.utmnews.repository.PostRepository;
import dsdmsa.utmnews.utils.Constants;

import static dsdmsa.utmnews.utils.Constants.ITEMS_PER_PAGE;
import static dsdmsa.utmnews.utils.Constants.TEXT_PLAIN;

@InjectViewState
public class LatestNewsPresenter extends MvpPresenter<LatestNewsFragmentVP.View> implements
        LatestNewsFragmentVP.Presenter,
        OnDataLoaded<List<Post>> {

    @Inject
    UtmServices services;

    @Inject
    PostRepository repository;

    private boolean isFirstPage = false;

    public LatestNewsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void loadNewsOnPage(int page) {
        getViewState().showProgressDialog();
        services.getNews(page, ITEMS_PER_PAGE, this);
        if (page == Constants.INITIAL_PAGE) {
            isFirstPage = true;
        }
    }

    @Override
    public void shareString(String str) {
        Intent sendIntent = new Intent();
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, str);
        sendIntent.setType(TEXT_PLAIN);
        App.getAppComponent().getContext()
                .startActivity(Intent.createChooser(
                        sendIntent,
                        App.getAppComponent().getContext().getString(R.string.share_title))
                );
    }

    @Override
    public void bookmarkPost(Post post) {
        post.setBookmarked(!post.isBookmarked());
        repository.add(post);
    }

    @Override
    public void onSuccess(List<Post> response) {
        if (isFirstPage) {
            isFirstPage = false;
            getViewState().refreshDatas(response);
        } else {
            getViewState().addNewses(response);
        }
        getViewState().hideProgressDialog();
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }


}
