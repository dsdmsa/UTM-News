package dsdmsa.utmnews.data.interactor;


import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Tag;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TagInteractor {

    private UtmServices services;
    private AppDb appDb;

    @Inject
    public TagInteractor(UtmServices services, AppDb appDb) {
        this.services = services;
        this.appDb = appDb;
    }

    public void getTags(final Callback callback) {

        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                List<Tag> tags = appDb.getTagDao().getAllTags();
                if (tags != null) {
                    callback.onTagLoaded(tags);
                    services.getTags(new OnDataLoaded<List<Tag>>() {
                        @Override
                        public void onSuccess(final List<Tag> response) {
                            Single.fromCallable(new Callable<String>() {
                                @Override
                                public String call() throws Exception {
                                    if (response != null)
                                        appDb.getTagDao().addTag(response);
                                    return "";
                                }
                            })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();
                        }

                        @Override
                        public void onError(String errorMsg) {
                            callback.onError(errorMsg);
                        }
                    });
                } else {
                    services.getTags(new OnDataLoaded<List<Tag>>() {
                        @Override
                        public void onSuccess(final List<Tag> response) {
                            Single.fromCallable(new Callable<String>() {
                                @Override
                                public String call() throws Exception {
                                    if (response != null)
                                        appDb.getTagDao().addTag(response);
                                    return "";
                                }
                            })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe();

                            callback.onTagLoaded(response);
                        }

                        @Override
                        public void onError(String errorMsg) {
                            callback.onError(errorMsg);
                        }
                    });
                }


                return "";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


    }

    public interface Callback {
        void onTagLoaded(List<Tag> tagList);

        void onError(String errorMsg);
    }

}
