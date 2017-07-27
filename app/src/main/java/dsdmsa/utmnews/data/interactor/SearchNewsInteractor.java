package dsdmsa.utmnews.data.interactor;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import io.reactivex.Observable;


public class SearchNewsInteractor {

    private UtmApi api;
    private GetNewsUseCase getNewsUseCase;

    @Inject
    public SearchNewsInteractor(UtmApi api, GetNewsUseCase getNewsUseCase) {
        this.api = api;
        this.getNewsUseCase = getNewsUseCase;
    }

    public Observable<List<SimplePost>> getNews(String key, int page) {
        return getNewsUseCase.getNews(api.getSearchPosts(key, Constants.ITEMS_PER_PAGE, page));
    }

}
