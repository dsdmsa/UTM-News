package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.presentation.mvp.MainActivityVP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityVP.View>
        implements MainActivityVP.Presenter {

    @Inject
    UtmApi utmApi;

    public MainActivityPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void search(String s) {
        utmApi.getSearchPosts(s, 3, 1).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Timber.d("succes"+response.body().size());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Timber.d("error");
            }
        });
    }
}


