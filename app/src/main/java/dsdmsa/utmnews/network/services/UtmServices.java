package dsdmsa.utmnews.network.services;


import java.util.List;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.models.Tag;
import dsdmsa.utmnews.network.OnDataLoaded;
import dsdmsa.utmnews.network.api.UtmApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UtmServices {

    private UtmApi api;

    public UtmServices(UtmApi api) {
        this.api = api;
    }

    public void getNews(int page,
                        int pageItems,
                        final OnDataLoaded<List<Post>> dataLoaded) {
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

    public void getNewsByCategory(int categoryId,
                                  int page,
                                  int pageItems,
                                  final OnDataLoaded<List<Post>> dataLoaded) {
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

    public void getTags(final OnDataLoaded<List<Tag>> dataLoaded) {
        api.getTags().enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                dataLoaded.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                dataLoaded.onError("error getng categories");
            }
        });
    }

    public void getNewsByTag(int tagId,
                             int page,
                             int pageItems,
                             final OnDataLoaded<List<Post>> dataLoaded) {
        api.getPostsByTags(tagId, pageItems, page).enqueue(new Callback<List<Post>>() {
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

    public void searchposts(String kay,
                            int page,
                            int pageItems,
                            final OnDataLoaded<List<Post>> dataLoaded) {
        api.getSearchPosts(kay, pageItems, page).enqueue(new Callback<List<Post>>() {
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
