package dsdmsa.utmnews.fragments.presenter;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.models.News;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.controlers.UtmController;
import dsdmsa.utmnews.views.NewsView;

import static dsdmsa.utmnews.This.networkComponent;

public class NewsPresenter implements GetNewsPresenter {

    @Inject
    UtmController controller;

    private NewsView view;

    private OnDataLoaded<List<News>> getNews = new OnDataLoaded<List<News>>() {
        @Override
        public void onDatatLoaddedSuccesfull(List<News> response) {
            view.hideProgressDialog();
            view.showNews(response);
        }

        @Override
        public void onError(String errorMsg) {
            view.hideProgressDialog();
            view.showErrorMessage(errorMsg);
        }
    };

    private OnDataLoaded<List<News>> addNews = new OnDataLoaded<List<News>>() {
        @Override
        public void onDatatLoaddedSuccesfull(List<News> response) {
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
        controller.getNews(getNews);
    }

    @Override
    public void loarMoreNews(int page) {
        view.showPregressDialog();
        controller.getNews(addNews);
    }
}
