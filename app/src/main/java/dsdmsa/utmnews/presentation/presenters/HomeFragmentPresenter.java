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
import dsdmsa.utmnews.presentation.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.presentation.fragments.NewsListFragment;
import dsdmsa.utmnews.presentation.mvp.HomeContract;
import timber.log.Timber;


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
        Timber.d("category loaded");
        getViewState().hideProgressDialog();

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new NewsListFragment());

        for (Category category : categories) {
            CategoryNewsFragment categoryNewsFragment = CategoryNewsFragment.newInstance(category);
            categoryNewsFragment.setCategory(category);
            fragments.add(categoryNewsFragment);
        }

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
