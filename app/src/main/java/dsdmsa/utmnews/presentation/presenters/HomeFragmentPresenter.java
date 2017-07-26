package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.interactor.CategoryInteractor;
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
                .flatMapIterable(categories -> categories)
                .map(CategoryNewsFragment::newInstance)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fragments -> {
                            List<BaseFragment> baseFragments = new ArrayList<>();
                            baseFragments.add(new NewsListFragment());
                            baseFragments.addAll(fragments);
                            getViewState().displayPages(baseFragments);
                            getViewState().hideProgressDialog();
                        },
                        error -> {
                            getViewState().hideProgressDialog();
                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

}
