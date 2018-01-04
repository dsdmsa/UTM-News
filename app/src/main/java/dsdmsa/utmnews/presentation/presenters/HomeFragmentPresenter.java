package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.interactor.CategoryInteractor;
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            getViewState().hideProgressDialog();
                            getViewState().updateCategories(categories);
                            getViewState().hideRefreshLaout();
                        },
                        error -> {
                            getViewState().showRefreshLayout();
                            getViewState().showInfoMessage(context.getString(R.string.error_loading_categories));
                            Timber.e(error.getMessage());
                            getViewState().hideProgressDialog();
                        }
                );
    }

}
