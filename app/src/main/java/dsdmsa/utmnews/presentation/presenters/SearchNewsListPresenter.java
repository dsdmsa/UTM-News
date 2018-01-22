package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.NewsInteractor;
import dsdmsa.utmnews.data.interactor.SearchNewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.SearchNewsContract;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


@InjectViewState
public class SearchNewsListPresenter extends MvpPresenter<SearchNewsContract.View> implements
        SearchNewsContract.Presenter {

    @Inject
    SearchNewsInteractor interactor;

    @Inject
    NewsInteractor newsInteractor;

    @Inject
    AppDb appDb;

    @Inject
    Context context;


    private CompositeDisposable disposables = new CompositeDisposable();
    private List<SimplePost> simplePosts = new ArrayList<>();

    public SearchNewsListPresenter() {
        App.getAppComponent().inject(this);

        disposables.add(appDb.getPostDao()
                .getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bookmarkedPosts -> {
                            for (int i = 0; i < simplePosts.size(); i++) {
                                if (bookmarkedPosts.contains(simplePosts.get(i))) {
                                    simplePosts.get(i).setBookmarked(true);
                                } else {
                                    simplePosts.get(i).setBookmarked(false);
                                }
                            }
                            getViewState().addNewses(this.simplePosts);
                        }, error -> {
                            Timber.e(error.getMessage());
                        }
                ));
    }

    @Override
    public void getNews(int page, String searchTerm) {
        disposables.add(
                interactor.getNews(searchTerm, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(simplePosts -> {
                                    this.simplePosts.addAll(simplePosts);
                                    if (this.simplePosts.isEmpty()) {
                                        getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                                    } else {
                                        getViewState().addNewses(this.simplePosts);
                                        getViewState().hideInfoMessage();
                                    }
                                    getViewState().hideBottomLoadingView();
                                }, error -> {
                                    getViewState().hideBottomLoadingView();
                                    Timber.e(error.getMessage());
                                }
                        ));
    }

    @Override
    public void refreshNewses(String searchTerm) {
        getViewState().showProgressDialog();
        this.simplePosts.clear();
        disposables.add(
                interactor.getNews(searchTerm, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(simplePosts -> {
                                    this.simplePosts.addAll(simplePosts);
                                    if (this.simplePosts.isEmpty()) {
                                        getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                                    } else {
                                        getViewState().clearList();
                                        getViewState().addNewses(this.simplePosts);
                                        getViewState().hideInfoMessage();
                                    }
                                    getViewState().hideProgressDialog();
                                }, error -> {
                                    getViewState().hideProgressDialog();
                                    Timber.e(error.getMessage());
                                }
                        ));
    }

    @Override
    public void bookmark(final SimplePost post) {
        getViewState().showProgressDialog();
        Observable<SimplePost> dbActionObservable;
        if (post.isBookmarked()) {
            post.setBookmarked(false);
            dbActionObservable = newsInteractor.removePost(post);
        } else {
            post.setBookmarked(true);
            dbActionObservable = newsInteractor.addPost(post);
        }
        disposables.add(
                dbActionObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                simplePost -> {
                                    getViewState().hideProgressDialog();
                                },
                                error -> {
                                    getViewState().hideProgressDialog();
                                    Timber.e( error.getMessage());
                                }
                        ));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
