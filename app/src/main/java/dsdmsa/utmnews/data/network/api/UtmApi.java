package dsdmsa.utmnews.data.network.api;


import java.util.List;

import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.Tag;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UtmApi {

    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getPosts(
            @Query("per_page") int per_page,
            @Query("page") int page
    );

    @GET("/wp-json/wp/v2/categories")
    Call<List<Category>> getCategories();

    @GET("/wp-json/wp/v2/tags")
    Call<List<Tag>> getTags();

    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getPostsByCategories(
            @Query("categories") int categoryId,
            @Query("per_page") int per_page,
            @Query("page") int page
    );

    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getSearchPosts(
            @Query("search") String searchKey,
            @Query("per_page") int perPage,
            @Query("page") int page
    );

    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getPostsByTags(
            @Query("tags") int tagId,
            @Query("per_page") int per_page,
            @Query("page") int page
    );


}
