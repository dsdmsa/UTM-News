package dsdmsa.utmnews.repository;


import java.util.List;

import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.network.OnDataLoaded;
import io.realm.Realm;
import io.realm.RealmResults;

public class GetAllRealmPostsSpecification implements Specification{

    private RealmResults<Post> realmResults;
    private OnDataLoaded<List<Post>> onDataLoaded;

    public GetAllRealmPostsSpecification(OnDataLoaded<List<Post>> onDataLoaded) {
        this.onDataLoaded = onDataLoaded;
    }

    @Override
    public void querry() {
        DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(Post.class).findAll();
                if (realmResults.isEmpty()){
                    onDataLoaded.onError("Data not found");
                }else {
                    onDataLoaded.onSuccess(realmResults);
                }
            }
        });
    }

}
