package dsdmsa.utm_news.network.controlers;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utm_news.models.News;
import dsdmsa.utm_news.network.OnDataLoaded;
import dsdmsa.utm_news.network.services.UtmServices;

import static dsdmsa.utm_news.This.networkComponent;

public class UtmController {

    @Inject
    UtmServices services;

    public UtmController(UtmServices services) {
        this.services = services;
        networkComponent.inject(this);
    }

    public void getNews(OnDataLoaded<List<News>> dataLoaded){
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

}
