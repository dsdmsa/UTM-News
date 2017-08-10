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

        Observable<List<Tag>> network = api.getTags()
                .doOnNext(tags -> appDb.getTagDao().addTag(tags)).subscribeOn(Schedulers.io());

        Observable<List<Tag>> local = Observable.fromCallable(() -> appDb.getTagDao().getAllTags())
                .filter(t -> !t.isEmpty())
                .switchIfEmpty(network)
                .subscribeOn(Schedulers.io());

        network.subscribeOn(Schedulers.io()).subscribe(t -> {},r -> {});

        return local
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
