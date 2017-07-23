package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.SearchNewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.presentation.mvp.SearchNewsContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class SearchNewsListPresenter extends MvpPresenter<SearchNewsContract.View> implements
        SearchNewsContract.Presenter,
        SearchNewsInteractor.Callback {

    @Inject
    SearchNewsInteractor interactor;

    @Inject
    AppDb appDb;

    private String key;

    public SearchNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void setSearchKey(String key) {
        this.key = key;
    }

    @Override
    public void getNews(int page) {
        getViewState().showProgressDialog();
        interactor.getNews(key, page, Constants.ITEMS_PER_PAGE, this);
    }

    @Override
    public void refreshNewses() {
        getViewState().showProgressDialog();
        interactor.getNews(key, 1, Constants.ITEMS_PER_PAGE, new SearchNewsInteractor.Callback() {
            @Override
            public void onSuccess(List<SimplePost> response) {
                getViewState().clearList();
                SearchNewsListPresenter.this.onSuccess(response);
            }

            @Override
            public void onError(String errorMsg) {
                SearchNewsListPresenter.this.onError(errorMsg);
            }
        });
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
