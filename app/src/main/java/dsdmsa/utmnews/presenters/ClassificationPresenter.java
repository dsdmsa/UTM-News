package dsdmsa.utmnews.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Tag;
import dsdmsa.utmnews.mvp.ClasificationVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;

/**
 * Created by dsdmsa on 4/8/17.
 */

@InjectViewState
public class ClassificationPresenter extends MvpPresenter<ClasificationVP.View> implements ClasificationVP.Presenter {

    @Inject
    UtmServices services;

    private OnDataLoaded<List<Category>> categoryLoaded = new OnDataLoaded<List<Category>>() {
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
    };

    private OnDataLoaded<List<Tag>> tagLoaded = new OnDataLoaded<List<Tag>>() {
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
    };

    public ClassificationPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getTagList() {
        services.getTags(tagLoaded);
        getViewState().showProgressDialog();
    }

    @Override
    public void getCategoryList() {
        services.getCategories(categoryLoaded);
        getViewState().showProgressDialog();
    }
}
