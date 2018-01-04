package dsdmsa.utmnews.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import dsdmsa.utmnews.data.db.dao.CategoryDao;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.utils.BooleanTypeConverter;


//@Database(entities = {Category.class, SimplePost.class, Tag.class}, version = 1)
@Database(entities = {Category.class}, version = 1)
@TypeConverters({BooleanTypeConverter.class})
public abstract class AppDb extends RoomDatabase {

//    public abstract SimplePostDao getPostDao();

    public abstract CategoryDao getCategoryDao();

//    public abstract TagDao getTagDao();

}
