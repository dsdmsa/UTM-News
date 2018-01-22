package dsdmsa.utmnews.data.interactor;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
import io.reactivex.Observable;

public class CategoryInteractor {

    private UtmApi api;
    private AppDb appDb;

    @Inject
    public CategoryInteractor(UtmApi api, AppDb appDb) {
        this.api = api;
        this.appDb = appDb;
    }

    public Observable<List<Category>> getCategories() {
        return api.getCategories().doOnNext(categories -> appDb.getCategoryDao().addCategories(categories));
    }

}
