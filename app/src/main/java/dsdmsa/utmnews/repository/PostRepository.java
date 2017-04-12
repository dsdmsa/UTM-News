package dsdmsa.utmnews.repository;


import dsdmsa.utmnews.models.SimplePost;
import io.realm.Realm;

public class PostRepository implements Repository<SimplePost> {

    @Override
    public void add(final SimplePost post) {
        Specification.DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(post);
            }
        });
    }

    @Override
    public void delete(final SimplePost post) {
        Specification.DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                post.removeFromRealm();
            }
        });
    }

    @Override
    public void querry(Specification specification) {
        specification.querry();
    }
    
}
