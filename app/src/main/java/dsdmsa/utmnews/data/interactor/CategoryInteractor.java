package dsdmsa.utmnews.data.interactor;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
import io.reactivex.Observable;

public class CategoryInteractor {

    private UtmApi api;

    @Inject
    public CategoryInteractor(UtmApi api) {
        this.api = api;
    }

    public Observable<List<Category>> getCategories() {
        return api.getCategories();
    }

}
