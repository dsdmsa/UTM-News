package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.interactor.CategoryNewsInteractor;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.CategoryContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class CategoryNewsListPresenter extends MvpPresenter<CategoryContract.View> implements
        CategoryContract.Presenter{

    @Inject
    CategoryNewsInteractor categoryInteractor;

    @Inject
    AppDb appDb;

    private Category category;

    public CategoryNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategoryNewses(int page) {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(category.getId(), page)
        .subscribe(
                simplePosts -> {
                    getViewState().hideProgressDialog();
                    getViewState().addNewses(simplePosts);
                },
                error ->{
                    getViewState().hideProgressDialog();
                    getViewState().showInfoMessage(error.getMessage());
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
                            getViewState().clearDatas();
                            getViewState().addNewses(simplePosts);
                        },
                        error ->{
                            getViewState().hideProgressDialog();
                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void bookmark(final SimplePost post) {
        Single.fromCallable(() -> {
            List<SimplePost> simplePosts = appDb.getPostDao().getAll();
            if (simplePosts.contains(post)) {
                appDb.getPostDao().delete(post);
            } else {
                appDb.getPostDao().addPost(post);
            }
            return "";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
