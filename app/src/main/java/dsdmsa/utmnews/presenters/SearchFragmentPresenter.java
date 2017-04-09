package dsdmsa.utmnews.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.SearchFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;

/**
 * Created by dsdmsa on 4/8/17.
 */

@InjectViewState
public class SearchFragmentPresenter extends MvpPresenter<SearchFragmentVP.View> implements
        SearchFragmentVP.Presenter,
        OnDataLoaded<List<Post>> {

    @Inject
    UtmServices services;

    @Override
    public void getCategoryNewses(String searchKey, int perPage, int page) {
        App.getAppComponent().inject(this);
        services.searchposts(searchKey, page, perPage, this);
        getViewState().showProgressDialog();
    }

    @Override
    public void onSuccess(List<Post> response) {
        getViewState().hideProgressDialog();
        getViewState().showNewses(response);
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }
}
