package dsdmsa.utmnews.network.services;


import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.api.UtmApi;
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
                if (response.body().size() > 0) {
                    dataLoaded.onSuccess(response.body());
                } else {
                    dataLoaded.onError(App.getAppComponent().getContext().getString(R.string.no_mer_data_info));
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                dataLoaded.onError(t.getMessage());
            }
        });
    }

    public void getCategories(final OnDataLoaded<List<Category>> dataLoaded) {
        api.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                dataLoaded.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                dataLoaded.onError("error getng categories");
            }
        });
    }

    public void getNewsByCategory(int categoryId, int page, int pageItems, final OnDataLoaded<List<Post>> dataLoaded) {
        api.getPostsByCategories(categoryId, pageItems, page).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.body().size() > 0) {
                    dataLoaded.onSuccess(response.body());
                } else {
                    dataLoaded.onError(App.getAppComponent().getContext().getString(R.string.no_mer_data_info));
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                dataLoaded.onError(t.getMessage());
            }
        });
    }
}