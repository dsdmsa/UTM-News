package dsdmsa.utmnews.data.interactor;


import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Tag;
import dsdmsa.utmnews.domain.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TagInteractor {

    private AppDb appDb;
    private Context context;
    private UtmApi api;

    @Inject
    public TagInteractor(UtmApi api, AppDb appDb, Context context) {
        this.api = api;
        this.appDb = appDb;
        this.context = context;
    }

    public Observable<List<Tag>> getTags() {
        if (Utils.isOnline(context)) {
            return api.getTags()
                    .map(tags -> {
                        appDb.getTagDao().addTag(tags);
                        return tags;
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return Observable.fromCallable(() -> appDb.getTagDao().getAllTags())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

}
