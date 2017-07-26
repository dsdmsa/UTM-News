package dsdmsa.utmnews.data.interactor;


import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryInteractor {

    private AppDb appDb;
    private UtmApi api;
    private Context context;

    @Inject
    public CategoryInteractor(AppDb appDb, UtmApi api, Context context) {
        this.appDb = appDb;
        this.api = api;
        this.context = context;
    }

    public Observable<List<Category>> getCategories() {
        if (Utils.isOnline(context)) {
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
