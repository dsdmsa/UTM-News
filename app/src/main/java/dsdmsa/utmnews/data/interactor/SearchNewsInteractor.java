package dsdmsa.utmnews.data.interactor;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class SearchNewsInteractor {

    private AppDb appDb;
    private UtmApi api;

    @Inject
    public SearchNewsInteractor(UtmApi api, AppDb appDb) {
        this.api = api;
        this.appDb = appDb;
    }

    public Observable<List<SimplePost>> getNews(String key, int page) {
        return api.getSearchPosts(key, Constants.ITEMS_PER_PAGE, page)
                .flatMapIterable(list -> list)
                .map(SimplePostAdapter::getSimplePost)
                .map(post -> {
                    post.setCategory(appDb.getCategoryDao().getCategory(post.getCategoryId()).getName());
                    return post;
                })
                .map(post -> {
                    post.setBookmarked(appDb.getPostDao().getPostById(post.getId()) != null);
                    Timber.d("post is bookmrked " + post.isBookmarked());
                    return post;
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
