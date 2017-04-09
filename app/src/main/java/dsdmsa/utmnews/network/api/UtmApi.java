package dsdmsa.utmnews.network.api;


import java.util.List;

import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Post;
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


    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getPostsByCategories(
            @Query("categories") int categoryId,
            @Query("per_page") int per_page,
            @Query("page") int page
    );

}
