package dsdmsa.utmnews.presentation.presenters;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsFragmentVP;
import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.data.repository.PostRepository;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;

import static dsdmsa.utmnews.domain.utils.Constants.ITEMS_PER_PAGE;
import static dsdmsa.utmnews.domain.utils.Constants.TEXT_PLAIN;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsFragmentVP.View> implements
        NewsFragmentVP.Presenter,
        OnDataLoaded<List<Post>> {

    @Inject
    UtmServices services;

    @Inject
    PostRepository repository;

    private boolean isFirstPage = false;

    public NewsPresenter() {
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
    public void getSearchedNewses(String searchKey, int perPage, int page) {
        App.getAppComponent().inject(this);
        services.searchposts(searchKey, page, perPage, this);
        getViewState().showProgressDialog();
    }

    @Override
    public void getCategoryNewses(int categoryId, int perPage, int page) {
        App.getAppComponent().inject(this);
        services.getNewsByCategory(categoryId, page, perPage, this);
        getViewState().showProgressDialog();
    }

    @Override
    public void getNewsByTag(int tagId, int perPage, int page) {
        services.getNewsByTag(tagId, page, perPage, this);
        getViewState().showProgressDialog();
    }

    @Override
    public void removeBookmarkPost(SimplePost post) {
        repository.delete(post);
    }

    @Override
    public void shareString(String str) {
        Intent sendIntent = new Intent();
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, str);
        sendIntent.setType(TEXT_PLAIN);
        App.getAppComponent().getApp()
                .startActivity(Intent.createChooser(
                        sendIntent,
                        App.getAppComponent().getApp().getString(R.string.share_title))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );
    }

    @Override
    public void bookmarkPost(SimplePost post) {
        repository.add(post);
    }

    @Override
    public void onSuccess(List<Post> response) {
        new PostParser(response).start();
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }

    public void setupRecyclerView() {
        getViewState().setupRecyclerView();
    }

    private class PostParser extends Thread {
        private List<Post> response;
        private List<SimplePost> simplePosts = new ArrayList<>();

        public PostParser(List<Post> response) {
            this.response = response;
        }

        @Override
        public void run() {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Post post : response) {
                simplePosts.add(SimplePostAdapter.getSimplePost(post));
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (isFirstPage) {
                        getViewState().refreshDatas(simplePosts);
                        isFirstPage = false;
                    } else {
                        getViewState().addNewses(simplePosts);
                    }
                    getViewState().hideProgressDialog();
                }
            });
        }
    }

}
