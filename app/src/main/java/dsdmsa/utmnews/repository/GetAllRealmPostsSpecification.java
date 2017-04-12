package dsdmsa.utmnews.repository;


import java.util.List;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.network.OnDataLoaded;
import io.realm.Realm;
import io.realm.RealmResults;

public class GetAllRealmPostsSpecification implements Specification {

    private RealmResults<SimplePost> realmResults;
    private OnDataLoaded<List<SimplePost>> onDataLoaded;

    public GetAllRealmPostsSpecification(OnDataLoaded<List<SimplePost>> onDataLoaded) {
        this.onDataLoaded = onDataLoaded;
    }

    @Override
    public void querry() {
        DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(SimplePost.class).findAll();
                if (realmResults.isEmpty()) {
                    onDataLoaded.onError(App.getAppComponent().getContext().getString(R.string.db_data_error));
                } else {
                    onDataLoaded.onSuccess(realmResults);
                }
            }
        });
    }

}
