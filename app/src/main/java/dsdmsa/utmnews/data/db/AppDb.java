package dsdmsa.utmnews.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import dsdmsa.utmnews.data.db.dao.CategoryDao;
import dsdmsa.utmnews.data.db.dao.PostDao;
import dsdmsa.utmnews.data.db.dao.TagDao;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.Tag;


@Database(entities = {Category.class, Post.class, Tag.class}, version = 1)
public abstract class AppDb extends RoomDatabase {

    public abstract PostDao getPostDao();

    public abstract CategoryDao getCategoryDao();

    public abstract TagDao getTagDao();

}
