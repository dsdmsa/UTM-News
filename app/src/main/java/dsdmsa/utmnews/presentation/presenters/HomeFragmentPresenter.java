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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class HomeFragmentPresenter extends MvpPresenter<HomeContract.View> implements
        HomeContract.Presenter {

    @Inject
    CategoryInteractor categoryInteractor;

    public HomeFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategories() {
        getViewState().showProgressDialog();
        categoryInteractor.getCategories()
                .map(categories -> {
                    List<BaseFragment> fragments = new ArrayList<>();
                    fragments.add(new NewsListFragment());
                    for (Category category : categories) {
                        CategoryNewsFragment categoryNewsFragment = CategoryNewsFragment.newInstance(category);
                        categoryNewsFragment.setCategory(category);
                        fragments.add(categoryNewsFragment);
                    }
                    return fragments;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fragments -> {
                            getViewState().displayPages(fragments);
                            getViewState().hideProgressDialog();
                        },
                        error -> {
                            getViewState().hideProgressDialog();
                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

}
