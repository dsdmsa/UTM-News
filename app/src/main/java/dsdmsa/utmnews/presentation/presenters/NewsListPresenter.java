package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.NewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static dsdmsa.utmnews.domain.utils.Constants.ITEMS_PER_PAGE;


@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsContract.View> implements NewsContract.Presenter, NewsInteractor.Callback {

    @Inject
    NewsInteractor interactor;

    @Inject
    AppDb appDb;

    public NewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getNews(int page) {
        getViewState().showProgressDialog();
        interactor.getNews(page, ITEMS_PER_PAGE, this);
    }

    @Override
    public void refreshNewses() {
        getViewState().showProgressDialog();
        interactor.getNews(1, ITEMS_PER_PAGE, new NewsInteractor.Callback() {
            @Override
            public void onSuccess(List<SimplePost> response) {
                getViewState().hideProgressDialog();
                getViewState().clearList();
                getViewState().addNewses(response);
            }

            @Override
            public void onError(String errorMsg) {
                getViewState().hideProgressDialog();
                getViewState().showInfoMessage(errorMsg);
            }
        });
    }

    @Override
    public void savePost(final SimplePost post) {
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
