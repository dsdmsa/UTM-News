package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
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
        SearchNewsContract.Presenter{

    @Inject
    SearchNewsInteractor interactor;

    @Inject
    AppDb appDb;

    @Inject
    Context context;

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
        interactor.getNews(key, page, Constants.ITEMS_PER_PAGE)
            .subscribe(
                    response -> {
                        getViewState().hideProgressDialog();
                        getViewState().addNewses(response);
                        if (response.isEmpty()){
                            getViewState().showInfoMessage(context.getString(R.string.search_no_results));
                        }else {
                            getViewState().hideInfoMessage();
                        }
                    },
                    error -> {
                        getViewState().hideProgressDialog();
//                        getViewState().showInfoMessage(error.getMessage());
                    }
            );
    }

    @Override
    public void refreshNewses() {
        getViewState().showProgressDialog();
        interactor.getNews(key, 1, Constants.ITEMS_PER_PAGE)
                .subscribe(
                        response -> {
                            getViewState().hideProgressDialog();
                            getViewState().clearList();
                            getViewState().addNewses(response);
                            if (response.isEmpty()){
                                getViewState().showInfoMessage(context.getString(R.string.search_no_results));
                            }else {
                                getViewState().hideInfoMessage();
                            }
                        },
                        error -> {
                            getViewState().hideProgressDialog();
//                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

    @Override
    public void bookmark(final SimplePost post) {
        Single.fromCallable(() -> {
            List<SimplePost> simplePosts = appDb.getPostDao().getAll();
            if (simplePosts.contains(post)) {
                getViewState().showInfoToast(context.getString(R.string.boocmark_removed));
                appDb.getPostDao().delete(post);
            } else {
                getViewState().showInfoToast(context.getString(R.string.boocmark_added));
                appDb.getPostDao().addPost(post);
            }
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
