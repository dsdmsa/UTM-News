package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.CategoryNewsInteractor;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.CategoryContract;


@InjectViewState
public class CategoryNewsListPresenter extends MvpPresenter<CategoryContract.View> implements CategoryContract.Presenter, CategoryNewsInteractor.Callback {

    @Inject
    CategoryNewsInteractor categoryInteractor;
    private Category category;

    public CategoryNewsListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategoryNewses(int page) {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(category.getId(), page, this);
    }

    @Override
    public void refresh() {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(category.getId(), 1, this);
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void onSuccess(List<SimplePost> response) {
        getViewState().hideProgressDialog();
        getViewState().addNewses(response);
    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);
    }
}
