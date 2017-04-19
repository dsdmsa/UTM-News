package dsdmsa.utmnews.repository;


import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.SimplePost;
import dsdmsa.utmnews.network.OnDataLoaded;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class GetAllRealmPostsSpecification implements Specification {

    private RealmResults<SimplePost> realmResults;
    private OnDataLoaded<RealmResults<SimplePost>> onDataLoaded;

    public GetAllRealmPostsSpecification(OnDataLoaded<RealmResults<SimplePost>> onDataLoaded) {
        this.onDataLoaded = onDataLoaded;
    }

    @Override
    public void querry() {
        Timber.d(" start querry ");
        DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Timber.d(" realm " + realm);
                realmResults = realm.where(SimplePost.class).findAll();
                Timber.d(" results " + realmResults.size());
                if (realmResults.isEmpty()) {
                    onDataLoaded.onError(App.getAppComponent().getApp().getString(R.string.db_data_error));
                } else {
                    onDataLoaded.onSuccess(realmResults);
                }
            }
        });
    }

}
