package dsdmsa.utmnews.presenters;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.LatestNewsFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.controlers.UtmServices;

import static dsdmsa.utmnews.App.appComponent;
import static dsdmsa.utmnews.utils.Constants.INITIAL_PAGE;
import static dsdmsa.utmnews.utils.Constants.PAGE_ITEMS;

@InjectViewState
public class LatestNewsPresenter extends MvpPresenter<LatestNewsFragmentVP.View> implements
        LatestNewsFragmentVP.Presenter {

    @Inject
    UtmServices controller;

    private OnDataLoaded<List<Post>> getNews = new OnDataLoaded<List<Post>>() {
        @Override
        public void onSuccess(List<Post> response) {
            getViewState().hideProgressDialog();
            getViewState().showNews(response);
        }

        @Override
        public void onError(String errorMsg) {
            getViewState().hideProgressDialog();
            getViewState().showInfoMessage(errorMsg);
        }
    };

    private OnDataLoaded<List<Post>> addNews = new OnDataLoaded<List<Post>>() {
        @Override
        public void onSuccess(List<Post> response) {
            getViewState().hideProgressDialog();
            getViewState().addNewses(response);
        }

        @Override
        public void onError(String errorMsg) {
            getViewState().hideProgressDialog();
            getViewState().showInfoMessage(errorMsg);
        }
    };

    public LatestNewsPresenter() {
        appComponent.inject(this);
    }


    @Override
    public void getNews() {
        getViewState().showProgressDialog();
        controller.getNews(INITIAL_PAGE, PAGE_ITEMS, getNews);
    }

    @Override
    public void loadMoreNews(int page) {
        getViewState().showProgressDialog();
        controller.getNews(page, PAGE_ITEMS, addNews);
    }
}
