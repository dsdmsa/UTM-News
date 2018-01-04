//package dsdmsa.utmnews.data.db.dao;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;
//
//import java.util.List;
//
//import dsdmsa.utmnews.domain.models.SimplePost;
//import io.reactivex.Flowable;
//
//@Dao
//public interface SimplePostDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void addPosts(List<SimplePost> posts);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void addPost(SimplePost post);
//
//    @Query("SELECT * FROM simplePost")
//    Flowable<List<SimplePost>> getAllPosts();
//
//    @Query("DELETE FROM simplePost")
//    void removeAllPosts();
//
//    @Query("SELECT * FROM simplePost")
//    List<SimplePost> getAll();
//
//    @Query("SELECT * FROM simplePost WHERE id LIKE :id LIMIT 1")
//    SimplePost getPostById(int id);
//
//
//    @Delete
//    void delete(SimplePost post);
//}
