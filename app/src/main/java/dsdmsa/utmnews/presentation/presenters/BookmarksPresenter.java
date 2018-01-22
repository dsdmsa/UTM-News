package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.NewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.BookmarsContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class BookmarksPresenter extends MvpPresenter<BookmarsContract.View> implements
        BookmarsContract.Presenter {

    private static final String TAG = BookmarksPresenter.class.getSimpleName();

    @Inject
    AppDb appDb;

    @Inject
    NewsInteractor newsInteractor;

    @Inject
    Context context;

    private CompositeDisposable disposables = new CompositeDisposable();

    public BookmarksPresenter() {
        App.getAppComponent().inject(this);
        disposables.add(appDb.getPostDao()
                .getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bookmarkedPosts -> getViewState().addNewses(bookmarkedPosts),
                        error -> Log.e(TAG, error.getMessage())
                ));
    }

    @Override
    public void refreshNewses() {
        getViewState().hideProgressDialog();
    }

    @Override
    public void bookmark(final SimplePost post) {
        disposables.add(newsInteractor.removePost(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        removedPost -> {
                        }, error -> Log.e(TAG, error.getMessage())
                ));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
