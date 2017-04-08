package dsdmsa.utmnews.network.controlers;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UtmServices {

    @Inject
    UtmApi api;

    public UtmServices(UtmApi api) {
        this.api = api;
        App.getAppComponent().inject(this);
    }

    public void getNews(int page, int pageItems, final OnDataLoaded<List<Post>> dataLoaded) {
        api.getPosts(pageItems, page).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                dataLoaded.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                dataLoaded.onError(t.getMessage());
            }
        });
    }
}
