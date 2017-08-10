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

        Observable<List<Category>> network = api.getCategories()
                .doOnNext(categories -> appDb.getCategoryDao().addCategories(categories));

        network.subscribeOn(Schedulers.newThread()).subscribe(l -> {}, r -> {});

        return Observable.fromCallable(() -> appDb.getCategoryDao().getAllCategories())
                .filter(l -> !l.isEmpty())
                .switchIfEmpty(network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
