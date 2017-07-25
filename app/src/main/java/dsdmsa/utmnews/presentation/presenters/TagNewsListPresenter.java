package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
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
        TagListContract.Presenter{

    @Inject
    TagsNewsInteractor interactor;

    @Inject
    AppDb appDb;

    @Inject
    Context context;

    private Tag tag;

    public TagNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategoryNewses(int page) {
        getViewState().showProgressDialog();
        interactor.getNews(tag.id, page)
        .subscribe(
                simplePosts -> {
                    getViewState().hideProgressDialog();
                    getViewState().addNewses(simplePosts);
                    if (simplePosts != null && simplePosts.isEmpty()) {
                        getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                    } else {
                        getViewState().addNewses(simplePosts);
                        getViewState().hideInfoMessage();
                    }
                },error -> {
                    getViewState().hideProgressDialog();
//                    getViewState().showInfoMessage(error.getMessage());
                }
        );
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        interactor.getNews(tag.id, 1)
                .subscribe(
                        simplePosts -> {
                            getViewState().hideProgressDialog();
                            getViewState().clear();
                            getViewState().addNewses(simplePosts);
                            if (simplePosts != null && simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                            } else {
                                getViewState().addNewses(simplePosts);
                                getViewState().hideInfoMessage();
                            }
                        },error -> {
                            getViewState().hideProgressDialog();
//                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

    @Override
    public void setTag(Tag tag) {
        this.tag = tag;
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
