package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.TagInteractor;
import dsdmsa.utmnews.presentation.mvp.TagContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class TagPresenter extends MvpPresenter<TagContract.View> implements
        TagContract.Presenter {

    @Inject
    TagInteractor tagInteractor;

    public TagPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getTags() {
        getViewState().showProgressDialog();
        tagInteractor.getTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tags -> {
                            getViewState().hideProgressDialog();
                            getViewState().showTags(tags);
                        },
                        error -> {
                            getViewState().hideProgressDialog();
                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        tagInteractor.getTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tags -> {
                            getViewState().clear();
                            getViewState().showTags(tags);
                            getViewState().hideProgressDialog();
                        },
                        error -> {
                            getViewState().hideProgressDialog();
                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

}
