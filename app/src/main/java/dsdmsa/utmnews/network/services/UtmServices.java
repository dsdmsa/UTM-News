package dsdmsa.utmnews.network.services;


import java.util.List;

import dsdmsa.utmnews.models.Post;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UtmServices {


    @GET("/wp-json/wp/v2/posts")
    Call<List<Post>> getPosts();


}
