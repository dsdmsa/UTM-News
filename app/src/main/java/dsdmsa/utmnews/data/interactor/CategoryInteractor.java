package dsdmsa.utmnews.data.interactor;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
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
        Observable<List<Category>> local = Observable.fromCallable(() -> appDb.getCategoryDao().getAllCategories());
        Observable<List<Category>> network = api.getCategories()
                .onErrorResumeNext(throwable -> local)
                .map(categories -> {
                    appDb.getCategoryDao().addCategories(categories);
                    return categories;
                });

        return local.mergeWith(network)
                .flatMapIterable(categories -> categories)
                .distinct(category -> category.id)
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

}
