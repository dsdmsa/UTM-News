package dsdmsa.utmnews.data.interactor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;


public class NewsInteractor {

    private UtmServices services;

    @Inject
    public NewsInteractor(UtmServices services) {
        this.services = services;
    }

   public void getNews(int page, int quantity, final Callback callback) {
        services.getNews(page, quantity, new OnDataLoaded<List<Post>>() {
            @Override
            public void onSuccess(List<Post> response) {
                List<SimplePost> simplePosts = new ArrayList<>();
                for (Post post : response) {
                    simplePosts.add(SimplePostAdapter.getSimplePost(post));
                }
                callback.onSuccess(simplePosts);
            }

            @Override
            public void onError(String errorMsg) {
                callback.onError(errorMsg);
            }
        });
    }

    public interface Callback {
        void onSuccess(List<SimplePost> response);

        void onError(String errorMsg);
    }

}
