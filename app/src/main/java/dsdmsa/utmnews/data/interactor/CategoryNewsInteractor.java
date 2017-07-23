package dsdmsa.utmnews.data.interactor;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;

public class CategoryNewsInteractor {

    private UtmServices services;
    private AppDb appDb;

    @Inject
    public CategoryNewsInteractor(UtmServices services, AppDb appDb) {
        this.services = services;
        this.appDb = appDb;
    }

    public void getCategories(int categoryId, int page, final Callback callback) {
        services.getNewsByCategory(categoryId, page, Constants.ITEMS_PER_PAGE, new OnDataLoaded<List<Post>>() {
            @Override
            public void onSuccess(List<Post> response) {
                List<SimplePost> simplePosts = new ArrayList<>();
                List<SimplePost> fromDb = appDb.getPostDao().getAllPosts().getValue();
                for (Post post : response) {
                    SimplePost simplePost = SimplePostAdapter.getSimplePost(post);
                    if (fromDb != null && fromDb.contains(simplePost)) {
                        simplePost.setBookmarked(true);
                    }
                    simplePosts.add(simplePost);
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
