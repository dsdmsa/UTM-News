package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.interactor.CategoryInteractor;
import dsdmsa.utmnews.presentation.fragments.BaseFragment;
import dsdmsa.utmnews.presentation.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.presentation.fragments.NewsListFragment;
import dsdmsa.utmnews.presentation.mvp.HomeContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


@InjectViewState
public class HomeFragmentPresenter extends MvpPresenter<HomeContract.View> implements
        HomeContract.Presenter {

    @Inject
    CategoryInteractor categoryInteractor;

    @Inject
    Context context;

    public HomeFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void getCategories() {
        getViewState().showProgressDialog();
        getViewState().showInfoMessage(context.getString(R.string.loaging_categories_info));
        categoryInteractor.getCategories()
                .flatMapIterable(categories -> categories)
                .filter(c -> c != null)
                .doOnNext(c -> Timber.d("cat : "+ c.name))
                .map(CategoryNewsFragment::newInstance)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fragments -> {
                            if (!fragments.isEmpty()) {
                                List<BaseFragment> baseFragments = new ArrayList<>();
                                baseFragments.addAll(fragments);
                                baseFragments.add(0, new NewsListFragment());
                                getViewState().displayPages(baseFragments);
                            } else {
                                getViewState().showInfoMessage(context.getString(R.string.error_loading_categories));
                            }
                            getViewState().hideProgressDialog();
                        },
                        error -> {
                            getViewState().hideProgressDialog();
                            getViewState().showInfoMessage(error.getMessage());
                        }
                );
    }

}
