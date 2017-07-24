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
            return api.getCategories()
                    .map(categories -> {
                        if (categories.isEmpty()) {
                            categories = appDb.getCategoryDao().getAllCategories();
                        } else {
                            appDb.getCategoryDao().addCategories(categories);
                        }
                        return categories;
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
    }

}
