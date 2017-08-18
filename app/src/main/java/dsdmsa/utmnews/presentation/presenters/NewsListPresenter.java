package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.NewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsContract.View> implements NewsContract.Presenter {

    @Inject
    NewsInteractor interactor;

    @Inject
    AppDb appDb;

    @Inject
    Context context;

    public NewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getNews(int page) {
        getViewState().showProgressDialog();
        Timber.d("GET NEWS ON PAGE " + page);
        interactor.getNews(page)
                .subscribe(simplePosts -> {
                            if (simplePosts != null && simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                                getViewState().hideProgressDialog();
                            } else {
                                getViewState().addNewses(simplePosts);
                                getViewState().hideInfoMessage();
                                getViewState().hideProgressDialog();
                            }
                        }, error -> {
                            getViewState().hideProgressDialog();
                        }
                );
    }

    @Override
    public void refreshNewses() {
        getViewState().showProgressDialog();
        interactor.getNews(1)
                .subscribe(simplePosts -> {
                            getViewState().clearList();
                            if (simplePosts != null && simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                                getViewState().hideProgressDialog();
                            } else {
                                getViewState().addNewses(simplePosts);
                                getViewState().hideInfoMessage();
                            }
                        }, error -> {
                            getViewState().hideProgressDialog();
                        }
                );
    }

    @Override
    public void savePost(final SimplePost post) {
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
