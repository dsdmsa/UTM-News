package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.BookmarsContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class BookmarksPresenter extends MvpPresenter<BookmarsContract.View> implements
        BookmarsContract.Presenter {

    @Inject
    AppDb appDb;

    @Inject
    Context context;

    public BookmarksPresenter() {
        App.getAppComponent().inject(this);
        appDb.getPostDao().getAllPosts().observeForever(simplePosts -> {
            getViewState().clearList();
            if (simplePosts != null && simplePosts.isEmpty()) {
                getViewState().showInfoMessage(context.getString(R.string.empty_bokmark_list));
            } else {
                getViewState().addNewses(simplePosts);
                getViewState().hideInfoMessage();
            }
        });
    }

    @Override
    public void refreshNewses() {
        getViewState().hideProgressDialog();
    }

    @Override
    public void bookmark(final SimplePost post) {
        Single.fromCallable(() -> {
            List<SimplePost> simplePosts = appDb.getPostDao().getAll();
            if (simplePosts.contains(post)) {
                appDb.getPostDao().delete(post);
                return context.getString(R.string.boocmark_removed);
            } else {
                appDb.getPostDao().addPost(post);
                return context.getString(R.string.boocmark_added);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(msg -> getViewState().showInfoToast(msg));
    }

}
