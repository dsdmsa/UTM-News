package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.NewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsContract;

import static dsdmsa.utmnews.domain.utils.Constants.ITEMS_PER_PAGE;


@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsContract.View> implements NewsContract.Presenter, NewsInteractor.Callback {

    @Inject
    NewsInteractor interactor;

    public NewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getNews(int page) {
        getViewState().showProgressDialog();
        interactor.getNews(page, ITEMS_PER_PAGE, this);
    }

    @Override
    public void refreshNewses() {
        getViewState().showProgressDialog();
        interactor.getNews(0, ITEMS_PER_PAGE, new NewsInteractor.Callback() {
            @Override
            public void onSuccess(List<SimplePost> response) {
                getViewState().hideProgressDialog();
                getViewState().clearList();
                getViewState().addNewses(response);
            }

            @Override
            public void onError(String errorMsg) {
                getViewState().hideProgressDialog();
                getViewState().showInfoMessage(errorMsg);
            }
        });
    }

    @Override
    public void onSuccess(List<SimplePost> response) {
        getViewState().hideProgressDialog();
        getViewState().addNewses(response);
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }
}
