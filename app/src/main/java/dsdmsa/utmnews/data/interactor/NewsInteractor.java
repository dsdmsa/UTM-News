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
//                .flatMapIterable(list -> list)
//                .map(SimplePostAdapter::getSimplePost)
//                .map(post -> {
//                    post.setCategory(appDb.getCategoryDao().getCategory(post.getCategoryId()).getName());
//                    return post;
//                })
//                .map(post -> {
//                    post.setBookmarked(appDb.getPostDao().getPostById(post.getId()) != null);
//                    Timber.d("post is bookmrked " + post.isBookmarked());
//                    return post;
//                })
//                .map(post -> {
//                    String date = post.getDate().substring(0, post.getDate().indexOf('T'));
//                    date = date.replace('-', ' ');
//                    String[] dates = date.split(Pattern.quote(" "));
//                    date = String.format("%s %s %s", dates[2], getMonth(Integer.valueOf(dates[1])), dates[0]);
//                    post.setDate(date);
//                    return post;
//                })
//                .toList()
//                .toObservable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    private String getMonth(int month) {
//        return new DateFormatSymbols().getMonths()[month - 1];
//    }

}
