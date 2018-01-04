package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.interactor.TagsNewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.presentation.mvp.TagListContract;


@InjectViewState
public class TagNewsListPresenter extends MvpPresenter<TagListContract.View> implements
        TagListContract.Presenter{

    @Inject
    TagsNewsInteractor interactor;

//    @Inject
//    AppDb appDb;

    @Inject
    Context context;

    private Tag tag;

    public TagNewsListPresenter() {
        App.getAppComponent().inject(this);
        getViewState().hideInfoMessage();
    }

    @Override
    public void getCategoryNewses(int page) {
        getViewState().showProgressDialog();
        interactor.getNews(tag.getId(), page)
        .subscribe(
                simplePosts -> {
                    getViewState().hideProgressDialog();
                    getViewState().hideInfoMessage();
                    if (simplePosts != null && simplePosts.isEmpty()) {
                        getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                    } else {
                        getViewState().addNewses(simplePosts);
                        getViewState().hideInfoMessage();
                    }
                },error -> {
                    getViewState().hideProgressDialog();
                }
        );
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        interactor.getNews(tag.getId(), 1)
                .subscribe(
                        simplePosts -> {
                            getViewState().hideProgressDialog();
                            getViewState().hideInfoMessage();
                            getViewState().clear();
                            if (simplePosts != null && simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                            } else {
                                getViewState().addNewses(simplePosts);
                                getViewState().hideInfoMessage();
                            }
                        },error -> {
                            getViewState().hideProgressDialog();
                        }
                );
    }

    @Override
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public void bookmark(final SimplePost post) {
//        Single.fromCallable(() -> {
//            List<SimplePost> simplePosts = appDb.getPostDao().getAll();
//            if (simplePosts.contains(post)) {
//                appDb.getPostDao().delete(post);
//                return context.getString(R.string.boocmark_removed);
//            } else {
//                appDb.getPostDao().addPost(post);
//                return context.getString(R.string.boocmark_added);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(msg -> getViewState().showInfoToast(msg));
    }
}
