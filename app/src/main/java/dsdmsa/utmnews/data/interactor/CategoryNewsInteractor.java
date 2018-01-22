package dsdmsa.utmnews.data.interactor;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import io.reactivex.Observable;

public class CategoryNewsInteractor {

    private UtmApi api;
    private GetNewsUseCase getNewsUseCase;

    @Inject
    public CategoryNewsInteractor(UtmApi api, GetNewsUseCase getNewsUseCase) {
        this.api = api;
        this.getNewsUseCase = getNewsUseCase;
    }

    public Observable<List<SimplePost>> getCategories(int categoryId, int page) {
        return getNewsUseCase.getNews(api.getPostsByCategories(categoryId, Constants.ITEMS_PER_PAGE, page));
    }
}
