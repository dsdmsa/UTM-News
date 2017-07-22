package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.TagInteractor;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.TagContract;


@InjectViewState
public class TagPresenter extends MvpPresenter<TagContract.View> implements
        TagContract.Presenter,
        TagInteractor.Callback {

    @Inject
    TagInteractor tagInteractor;

    public TagPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getTags() {
        getViewState().showProgressDialog();
        tagInteractor.getTags(this);
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        tagInteractor.getTags(new TagInteractor.Callback() {
            @Override
            public void onTagLoaded(List<Tag> tagList) {
                getViewState().clear();
                TagPresenter.this.onTagLoaded(tagList);
            }

            @Override
            public void onError(String errorMsg) {
                TagPresenter.this.onError(errorMsg);
            }
        });
    }

    @Override
    public void onTagLoaded(List<Tag> tagList) {
        getViewState().hideProgressDialog();
        getViewState().showTags(tagList);
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }
}
