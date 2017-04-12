package dsdmsa.utmnews.repository;


import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.models.SimplePost;
import io.realm.Realm;

public class PostRepository implements Repository<Post> {

    @Override
    public void add(final Post post) {
        Specification.DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(post);
            }
        });
    }

    @Override
    public void delete(final Post post) {
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

    public void add(final SimplePost post) {
        Specification.DATABASE.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(post);
            }
        });
    }
}
