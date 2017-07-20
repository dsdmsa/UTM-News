package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.ClasificationVP;


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
