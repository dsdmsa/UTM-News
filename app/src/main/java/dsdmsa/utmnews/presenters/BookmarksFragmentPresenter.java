package dsdmsa.utmnews.presenters;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.mvp.BookmarksFragmentVP;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.repository.GetAllRealmPostsSpecification;
import dsdmsa.utmnews.repository.PostRepository;

@InjectViewState
public class BookmarksFragmentPresenter extends MvpPresenter<BookmarksFragmentVP.View> implements
        BookmarksFragmentVP.Presenter,
        OnDataLoaded<List<SimplePost>> {

    @Inject
    PostRepository repository;

    public BookmarksFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void loadNews() {
        getViewState().showProgressDialog();
        repository.querry(new GetAllRealmPostsSpecification(this));
    }

    @Override
    public void removePost(SimplePost post) {
        repository.delete(post);
    }

    @Override
    public void onSuccess(List<SimplePost> response) {
        getViewState().addNewses(response);
        getViewState().hideProgressDialog();
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().showInfoMessage(errorMsg);
        getViewState().hideProgressDialog();
    }

}
