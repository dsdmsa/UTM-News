package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.TagsNewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.TagListContract;


@InjectViewState
public class TagNewsListPresenter extends MvpPresenter<TagListContract.View> implements
        TagListContract.Presenter,
        TagsNewsInteractor.Callback {

    @Inject
    TagsNewsInteractor interactor;

    private Tag tag;

    public TagNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategoryNewses(int page) {
        getViewState().showProgressDialog();
        interactor.getNews(tag.id, page, this);
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        interactor.getNews(tag.id, 1, this);
    }

    @Override
    public void setTag(Tag tag) {
        this.tag = tag;
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
