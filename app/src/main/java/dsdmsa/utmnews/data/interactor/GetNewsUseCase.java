package dsdmsa.utmnews.data.interactor;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.utils.PostUtils;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GetNewsUseCase {

    @Inject
    public GetNewsUseCase() {
    }

    public Observable<List<SimplePost>> getNews(Observable<List<Post>> observable) {
        return observable.flatMapIterable(list -> list)
                .map(PostUtils::toSimplePost)
                .map(PostUtils::setBookmarked)
                .map(PostUtils::setCategory)
                .map(PostUtils::setDate)
                .onErrorResumeNext(throwable -> {
                    return Observable.just(new SimplePost());
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}