package dsdmsa.utmnews.data.interactor;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.utils.Constants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryInteractor {

    private AppDb appDb;
    private UtmApi api;

    @Inject
    public CategoryInteractor(AppDb appDb, UtmApi api) {
        this.appDb = appDb;
        this.api = api;
    }

    public Observable<List<Category>> getCategories() {
        if (App.getAppComponent().getPrefs().getBoolean(Constants.IN_INTERNET_AVAIBLE, false)) {
            return api.getCategories()
                    .map(categories -> {
                        appDb.getCategoryDao().addCategories(categories);
                        return categories;
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        } else {
            return Observable.fromCallable(() -> appDb.getCategoryDao().getAllCategories())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        }
    }

}
