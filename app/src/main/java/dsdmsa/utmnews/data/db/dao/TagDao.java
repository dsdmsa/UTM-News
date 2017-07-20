package dsdmsa.utmnews.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import dsdmsa.utmnews.domain.models.Tag;

@Dao
public interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTag(List<Tag> posts);

    @Query("SELECT * FROM Tag")
    LiveData<List<Tag>> getAllTags();

    @Query("DELETE FROM Tag")
    void removeAll();
}
