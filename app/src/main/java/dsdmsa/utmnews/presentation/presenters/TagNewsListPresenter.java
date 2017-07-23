package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.TagsNewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.TagListContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class TagNewsListPresenter extends MvpPresenter<TagListContract.View> implements
        TagListContract.Presenter,
        TagsNewsInteractor.Callback {

    @Inject
    TagsNewsInteractor interactor;

    @Inject
    AppDb appDb;

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
    public void bookmark(final SimplePost post) {
        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                List<SimplePost> simplePosts = appDb.getPostDao().getAll();
                if (simplePosts.contains(post)) {
                    appDb.getPostDao().delete(post);
                } else {
                    appDb.getPostDao().addPost(post);
                }
                return "";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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
