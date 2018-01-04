package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.interactor.CategoryNewsInteractor;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.CategoryContract;


@InjectViewState
public class CategoryNewsListPresenter extends MvpPresenter<CategoryContract.View> implements
        CategoryContract.Presenter {

    @Inject
    CategoryNewsInteractor categoryInteractor;

//    @Inject
//    AppDb appDb;

    @Inject
    Context context;

    public CategoryNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    private Category category;


    @Override
    public void getCategoryNewses(int page) {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(category.getId(), page)
                .subscribe(
                        simplePosts -> {
                            getViewState().hideProgressDialog();
                            if (simplePosts != null && simplePosts.isEmpty()) {
                                getViewState().showInfoMessage(context.getString(R.string.empty_news_list));
                            } else {
                                getViewState().addNewses(simplePosts);
                                getViewState().hideInfoMessage();
                            }
                        },
                        error -> {
                            getViewState().hideProgressDialog();
                        }
                );
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(category.getId(), 1)
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
                            getViewState().hideProgressDialog();
                        }
                );
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
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
