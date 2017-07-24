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
        return api.getTags()
                .map(tags -> {
                    if (tags.isEmpty()) {
                        tags = appDb.getTagDao().getAllTags();
                    } else {
                        appDb.getTagDao().addTag(tags);
                    }
                    return tags;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
