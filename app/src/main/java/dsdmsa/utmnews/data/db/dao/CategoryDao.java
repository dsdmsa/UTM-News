package dsdmsa.utmnews.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import dsdmsa.utmnews.domain.models.Category;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategories(List<Category> posts);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();

    @Query("DELETE FROM Category")
    void removeAll();

    @Query("SELECT * FROM Category WHERE id LIKE :id LIMIT 1")
    Category getCategory(int id);
}
