package dsdmsa.utmnews.data.interactor;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import io.reactivex.Observable;


public class NewsInteractor {

    private GetNewsUseCase getNewsUseCase;
    private UtmApi api;
    private AppDb appDb;

    @Inject
    public NewsInteractor(GetNewsUseCase getNewsUseCase, UtmApi api, AppDb appDb) {
        this.getNewsUseCase = getNewsUseCase;
        this.api = api;
        this.appDb = appDb;
    }

    public Observable<List<SimplePost>> getNews(int page) {
        return getNewsUseCase.getNews(api.getPosts(Constants.ITEMS_PER_PAGE, page));
    }

    public Observable<SimplePost> addPost(SimplePost post) {
        return Observable.just(appDb)
                .map(AppDb::getPostDao)
                .doOnNext(simplePostDao -> simplePostDao.addPost(post))
                .map(s -> post);
    }

    public Observable<SimplePost> removePost(SimplePost post) {
        return Observable.just(appDb)
                .map(AppDb::getPostDao)
                .doOnNext(simplePostDao -> simplePostDao.delete(post))
                .map(s -> post);
    }
}
