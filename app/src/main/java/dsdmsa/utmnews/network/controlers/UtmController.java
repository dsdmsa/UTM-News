package dsdmsa.utmnews.network.controlers;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.News;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.services.UtmServices;

import static dsdmsa.utmnews.This.networkComponent;

public class UtmController {

    @Inject
    UtmServices services;

    public UtmController(UtmServices services) {
        this.services = services;
        networkComponent.inject(this);
    }

    public void getNews(OnDataLoaded<List<News>> dataLoaded){
        dataLoaded.onError("error");
//        services.getNews().enqueue(new Callback<List<News>>() {
//            @Override
//            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<News>> call, Throwable t) {
//
//            }
//        });
    }

    public void getCategories(OnDataLoaded<List<Category>> dataLoaded) {
        dataLoaded.onError("error");
    }
}
