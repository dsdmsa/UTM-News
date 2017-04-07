package dsdmsa.utmnews.fragments.presenter;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.controlers.UtmController;
import dsdmsa.utmnews.views.NewsView;

import static dsdmsa.utmnews.This.networkComponent;
import static dsdmsa.utmnews.utils.Constants.INITIAL_PAGE;
import static dsdmsa.utmnews.utils.Constants.PAGE_ITEMS;

public class NewsPresenter implements GetNewsPresenter {

    @Inject
    UtmController controller;

    private NewsView view;

    private OnDataLoaded<List<Post>> getNews = new OnDataLoaded<List<Post>>() {
        @Override
        public void onDatatLoaddedSuccesfull(List<Post> response) {
            view.hideProgressDialog();
            view.showNews(response);
        }

        @Override
        public void onError(String errorMsg) {
            view.hideProgressDialog();
            view.showErrorMessage(errorMsg);
        }
    };

    private OnDataLoaded<List<Post>> addNews = new OnDataLoaded<List<Post>>() {
        @Override
        public void onDatatLoaddedSuccesfull(List<Post> response) {
            view.hideProgressDialog();
            view.addNewses(response);
        }

        @Override
        public void onError(String errorMsg) {
            view.hideProgressDialog();
            view.showErrorMessage(errorMsg);
        }
    };

    public NewsPresenter(NewsView view) {
        this.view = view;
        networkComponent.inject(this);
    }


    @Override
    public void getNews() {
        view.showPregressDialog();
        controller.getNews(INITIAL_PAGE, PAGE_ITEMS, getNews);
    }

    @Override
    public void loarMoreNews(int page) {
        view.showPregressDialog();
        controller.getNews(page, PAGE_ITEMS, addNews);
    }
}
