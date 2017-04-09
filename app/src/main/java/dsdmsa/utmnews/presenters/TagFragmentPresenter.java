package dsdmsa.utmnews.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.Tag;
import dsdmsa.utmnews.mvp.TagsFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;

/**
 * Created by dsdmsa on 4/8/17.
 */

@InjectViewState
public class TagFragmentPresenter extends MvpPresenter<TagsFragmentVP.View> implements
        TagsFragmentVP.Presenter,
        OnDataLoaded<List<Tag>> {

    @Inject
    UtmServices services;

    public TagFragmentPresenter() {
        App.getAppComponent().inject(this);
        services.getTags(this);
        getViewState().showProgressDialog();
    }

    @Override
    public void onSuccess(List<Tag> response) {
        getViewState().hideProgressDialog();
        getViewState().showTags(response);
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }

}
