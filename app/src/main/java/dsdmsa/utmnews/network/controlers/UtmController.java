package dsdmsa.utmnews.network.controlers;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dsdmsa.utmnews.This.networkComponent;

public class UtmController {

    @Inject
    UtmServices services;

    public UtmController(UtmServices services) {
        this.services = services;
        networkComponent.inject(this);
    }

    public void getNews(int page, int pageItems, final OnDataLoaded<List<Post>> dataLoaded) {
        services.getPosts(pageItems, page).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                dataLoaded.onDatatLoaddedSuccesfull(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                dataLoaded.onError(t.getMessage());
            }
        });
    }
}
