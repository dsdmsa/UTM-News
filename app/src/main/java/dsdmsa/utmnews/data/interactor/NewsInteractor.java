package dsdmsa.utmnews.data.interactor;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import io.reactivex.Observable;


public class NewsInteractor {

    private GetNewsUseCase getNewsUseCase;
    private UtmApi api;

    @Inject
    public NewsInteractor(GetNewsUseCase getNewsUseCase, UtmApi api) {
        this.getNewsUseCase = getNewsUseCase;
        this.api = api;
    }

    public Observable<List<SimplePost>> getNews(int page) {
        return getNewsUseCase.getNews(api.getPosts(Constants.ITEMS_PER_PAGE, page));
    }

}
