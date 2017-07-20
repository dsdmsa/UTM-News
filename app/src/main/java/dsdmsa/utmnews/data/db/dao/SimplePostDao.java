package dsdmsa.utmnews.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;

@Dao
public interface SimplePostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPosts(List<SimplePost> posts);

    @Query("SELECT * FROM simplePost")
    LiveData<List<SimplePost>> getAllPosts();

    @Query("DELETE FROM simplePost")
    void removeAllPosts();
}
