package dsdmsa.utmnews.data.interactor;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Tag;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TagInteractor {

    private AppDb appDb;
    private UtmApi api;

    @Inject
    public TagInteractor(UtmApi api, AppDb appDb) {
        this.api = api;
        this.appDb = appDb;
    }

    public Observable<List<Tag>> getTags() {
        Observable<List<Tag>> local = Observable.fromCallable(() -> appDb.getTagDao().getAllTags());
        Observable<List<Tag>> network = api.getTags()
                .onErrorResumeNext(throwable -> local)
                .doOnNext(tags -> appDb.getTagDao().addTag(tags));
        return Observable.merge(
                local.subscribeOn(Schedulers.io()),
                network.subscribeOn(Schedulers.io())
        ).flatMapIterable(tags -> tags)
                .distinct(tag -> tag.getId())
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

}
