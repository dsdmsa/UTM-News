package dsdmsa.utmnews.presentation.presenters;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.BookmarsContract;


@InjectViewState
public class BookmarksPresenter extends MvpPresenter<BookmarsContract.View> implements BookmarsContract.Presenter{

    @Inject
    AppDb db;

    public BookmarksPresenter() {
        App.getAppComponent().inject(this);
        db.getPostDao().getAllPosts().observeForever(new Observer<List<SimplePost>>() {
            @Override
            public void onChanged(@Nullable List<SimplePost> simplePosts) {
                getViewState().clearList();
                getViewState().addNewses(simplePosts);
            }
        });
    }

    @Override
    public void refreshNewses() {
        getViewState().hideProgressDialog();
    }

}
