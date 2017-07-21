package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.CategoryInteractor;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.presentation.fragments.BaseFragment;
import dsdmsa.utmnews.presentation.fragments.NewsListFragment;
import dsdmsa.utmnews.presentation.mvp.HomeContract;


@InjectViewState
public class HomeFragmentPresenter extends MvpPresenter<HomeContract.View> implements HomeContract.Presenter, CategoryInteractor.Callback {

    @Inject
    CategoryInteractor categoryInteractor;

    public HomeFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategories() {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories(this);
    }

    @Override
    public void onCategoryLoaded(List<Category> categories) {
        getViewState().hideProgressDialog();

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new NewsListFragment());


        getViewState().displayPages(fragments);
    }

    @Override
    public void onTagNewsLoaded() {
        getViewState().hideProgressDialog();

    }

    @Override
    public void onError(String errorMsg) {
        getViewState().hideProgressDialog();
        getViewState().showInfoMessage(errorMsg);

    }
}
