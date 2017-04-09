package dsdmsa.utmnews.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.mvp.CategoriesFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;

/**
 * Created by dsdmsa on 4/8/17.
 */

@InjectViewState
public class CategoriesFragmentPresenter extends MvpPresenter<CategoriesFragmentVP.View> implements
        CategoriesFragmentVP.Presenter,
        OnDataLoaded<List<Category>> {

    @Inject
    UtmServices services;

    public CategoriesFragmentPresenter() {
        App.getAppComponent().inject(this);
        services.getCategories(this);
        getViewState().showProgressDialog();
    }

    @Override
    public void onSuccess(List<Category> response) {
        getViewState().hideProgressDialog();
        getViewState().showCategories(response);
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }

}
