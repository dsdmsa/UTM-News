package dsdmsa.utmnews.data.interactor;


import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Category;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryInteractor {

    private UtmServices services;
    private AppDb appDb;

    @Inject
    public CategoryInteractor(UtmServices services, AppDb appDb) {
        this.services = services;
        this.appDb = appDb;
    }

    public void getCategories(final Callback callback) {

        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                List<Category> categories = appDb.getCategoryDao().getAllCategories();
                if (categories != null)
                    callback.onCategoryLoaded(categories);
                return "";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


        services.getCategories(new OnDataLoaded<List<Category>>() {
            @Override
            public void onSuccess(final List<Category> response) {
                Single.fromCallable(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        if (response != null)
                            appDb.getCategoryDao().addCategories(response);
                        return "";
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                callback.onCategoryLoaded(response);
            }

            @Override
            public void onError(String errorMsg) {
                callback.onError(errorMsg);
            }
        });
    }

    public interface Callback {
        void onCategoryLoaded(List<Category> tagList);

        void onTagNewsLoaded();

        void onError(String errorMsg);
    }

}
