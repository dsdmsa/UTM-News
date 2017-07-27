package dsdmsa.utmnews.data.interactor;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class GetNewsUseCase {

    private AppDb appDb;

    @Inject
    public GetNewsUseCase(AppDb appDb) {
        this.appDb = appDb;
    }

    public Observable<List<SimplePost>> getNews(Observable<List<Post>> observable) {
        return observable.flatMapIterable(list -> list)
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
                .map(post -> {
                    String date = post.getDate().substring(0, post.getDate().indexOf('T'));
                    date = date.replace('-', ' ');
                    String[] dates = date.split(Pattern.quote(" "));
                    date = String.format("%s %s %s", dates[2], getMonth(Integer.valueOf(dates[1])), dates[0]);
                    post.setDate(date);
                    return post;
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

}
