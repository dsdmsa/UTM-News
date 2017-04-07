package dsdmsa.utmnews.network.services;


import java.util.List;

import dsdmsa.utmnews.models.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UtmServices {


    //http://utm.md/wp-json/wp/v2/posts?per_page=1&page=2

    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getPosts(
            @Query("per_page") int per_page,
            @Query("page") int page
    );


}
