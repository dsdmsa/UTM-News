package dsdmsa.utmnews.data.interactor;


import java.util.ArrayList;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Tag;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TagInteractor {

    private UtmApi api;

    @Inject
    public TagInteractor(UtmApi api) {
        this.api = api;
    }

    public Observable<ArrayList<Tag>> getTags() {
        return api.getTags().map(ArrayList::new);
    }

}
