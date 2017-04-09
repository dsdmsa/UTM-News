package dsdmsa.utmnews.presenters;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.LatestNewsFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;

import static dsdmsa.utmnews.utils.Constants.ITEMS_PER_PAGE;

@InjectViewState
public class LatestNewsPresenter extends MvpPresenter<LatestNewsFragmentVP.View> implements
        LatestNewsFragmentVP.Presenter,
        OnDataLoaded<List<Post>> {

    @Inject
    UtmServices services;

    public LatestNewsPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void loadNewsOnPage(int page) {
        getViewState().showProgressDialog();
        services.getNews(page, ITEMS_PER_PAGE, this);
    }

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
}
