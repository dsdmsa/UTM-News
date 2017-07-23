package dsdmsa.utmnews.data.interactor;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Category;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static dsdmsa.utmnews.domain.utils.Constants.IN_INTERNET_AVAIBLE;

public class CategoryInteractor {

    private UtmServices services;
    private AppDb appDb;
    private UtmApi api;

    @Inject
    public CategoryInteractor(UtmServices services, AppDb appDb, UtmApi api) {
        this.services = services;
        this.appDb = appDb;
        this.api = api;
    }

    public void getCategories(final Callback callback) {
        Single.fromCallable(new Callable<List<Category>>() {
            @Override
            public List<Category> call() throws Exception {
                return appDb.getCategoryDao().getAllCategories();
            }
        }).map(new Function<List<Category>, List<Category>>() {
            @Override
            public List<Category> apply(List<Category> categories) throws Exception {
                if (!categories.isEmpty()) {
                    return categories;
                } else {
                    if (App.getAppComponent().getPrefs().getBoolean(IN_INTERNET_AVAIBLE, false)) {
                        List<Category> cats = api.getCategories().execute().body();
                        appDb.getCategoryDao().addCategories(cats);
                        return cats;
                    } else {
                        return new ArrayList<>();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        callback.onCategoryLoaded(categories);
                    }
                });
    }

    public interface Callback {
        void onCategoryLoaded(List<Category> tagList);

        void onError(String errorMsg);
    }

}
