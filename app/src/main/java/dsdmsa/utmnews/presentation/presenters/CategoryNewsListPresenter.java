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
import dsdmsa.utmnews.data.interactor.CategoryNewsInteractor;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.CategoryContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class CategoryNewsListPresenter extends MvpPresenter<CategoryContract.View> implements
        CategoryContract.Presenter {

    private static final String TAG = CategoryNewsListPresenter.class.getSimpleName();

    @Inject
    CategoryNewsInteractor categoryInteractor;

//    @Inject
//    AppDb appDb;

    @Inject
    Context context;

    private List<SimplePost> simplePosts = new ArrayList<>();

    public CategoryNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategoryNewses(int page, int categoryId) {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(categoryId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        simplePosts -> {
                            getViewState().hideProgressDialog();
                            this.simplePosts.addAll(simplePosts);
                            if (this.simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                            } else {
                                getViewState().addNewses(this.simplePosts);
                                getViewState().hideInfoMessage();
                            }
                        },
                        error -> {
                            Log.e(TAG, error.getMessage());
                            getViewState().hideProgressDialog();
                        }
                );
    }

    @Override
    public void refresh(int categoryId) {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(categoryId, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        simplePosts -> {
                            getViewState().hideProgressDialog();
                            getViewState().clearDates();
                            if (simplePosts != null && simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                            } else {
                                getViewState().addNewses(simplePosts);
                                getViewState().hideInfoMessage();
                            }
                        },
                        error -> {
                            Log.e(TAG, error.getMessage());
                            getViewState().hideProgressDialog();
                        }
                );
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
