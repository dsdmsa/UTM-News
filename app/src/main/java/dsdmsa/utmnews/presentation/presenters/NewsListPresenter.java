package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.interactor.NewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsContract;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsContract.View> implements NewsContract.Presenter {

    private static final String TAG = NewsListPresenter.class.getSimpleName();

    @Inject
    NewsInteractor interactor;

    @Inject
    Context context;

    private CompositeDisposable disposables = new CompositeDisposable();
    private List<SimplePost> simplePosts = new ArrayList<>();
    private List<SimplePost> bookmarkedPostas = new ArrayList<>();

    public NewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getNews(int page) {
        getViewState().showProgressDialog();
        Timber.d("GET NEWS ON PAGE " + page);
        disposables.add(
                interactor.getNews(page)
                        .flatMapIterable(l -> l)
                        .map(post -> {
                            if (bookmarkedPostas.contains(post)) {
                                post.setBookmarked(true);
                            }
                            return post;
                        })
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(simplePosts -> {
                                    this.simplePosts.addAll(simplePosts);
                                    if (this.simplePosts.isEmpty()) {
                                        getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                                        getViewState().hideProgressDialog();
                                    } else {
                                        getViewState().addNewses(this.simplePosts);
                                        getViewState().hideInfoMessage();
                                        getViewState().hideProgressDialog();
                                    }
                                }, error -> {
                                    getViewState().hideProgressDialog();
                                    Log.e(TAG, error.getMessage());
                                }
                        ));
    }

    @Override
    public void refreshNewses() {
        getViewState().showProgressDialog();
        this.simplePosts.clear();
        disposables.add(
                interactor.getNews(1)
                        .flatMapIterable(l -> l)
                        .map(post -> {
                            if (bookmarkedPostas.contains(post)) {
                                post.setBookmarked(true);
                            }
                            return post;
                        })
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(simplePosts -> {
                                    this.simplePosts.addAll(simplePosts);
                                    getViewState().clearList();
                                    if (this.simplePosts.isEmpty()) {
                                        getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                                        getViewState().hideProgressDialog();
                                    } else {
                                        getViewState().addNewses(this.simplePosts);
                                        getViewState().hideInfoMessage();
                                    }
                                }, error -> {
                                    getViewState().hideProgressDialog();
                                    Log.e(TAG, error.getMessage());
                                }
                        ));
    }

    @Override
    public void savePost(final SimplePost post) {
        getViewState().showProgressDialog();

        Observable<SimplePost> dbActionObservable;

        if (post.isBookmarked()) {
            dbActionObservable = interactor.removePost(post);
            this.bookmarkedPostas.add(post);
        } else {
            dbActionObservable = interactor.addPost(post);
            this.bookmarkedPostas.remove(post);
        }
        int index = this.simplePosts.indexOf(post);
        this.simplePosts.get(index).setBookmarked(!post.isBookmarked());
        getViewState().addNewses(this.simplePosts);

        disposables.add(
                dbActionObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                simplePost -> {
                                    if (simplePost.isBookmarked()) {
                                        getViewState().showInfoToast("post added");
                                    } else {
                                        getViewState().showInfoToast("post removed");
                                    }
                                    getViewState().hideProgressDialog();
                                },
                                error -> {
                                    getViewState().hideProgressDialog();
                                    Log.e(TAG, error.getMessage());
                                }
                        ));
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
