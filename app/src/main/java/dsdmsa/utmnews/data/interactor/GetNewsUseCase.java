package dsdmsa.utmnews.data.interactor;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.utils.PostUtils;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import io.reactivex.Observable;


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
                .onErrorResumeNext(Observable.empty())
                .toList()
                .toObservable();
    }

}
