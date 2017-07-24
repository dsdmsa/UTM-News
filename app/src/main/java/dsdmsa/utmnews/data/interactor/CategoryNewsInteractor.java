package dsdmsa.utmnews.data.interactor;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryNewsInteractor {

    private AppDb appDb;
    private UtmApi api;

    @Inject
    public CategoryNewsInteractor(UtmApi api, AppDb appDb) {
        this.api = api;
        this.appDb = appDb;
    }

    public Observable<List<SimplePost>> getCategories(int categoryId, int page) {

        return api.getPostsByCategories(categoryId, page, Constants.ITEMS_PER_PAGE)
                .map(posts -> {
                    List<SimplePost> simplePosts = new ArrayList<>();
                    for (Post post : posts) {
                        SimplePost simplePost = SimplePostAdapter.getSimplePost(post);
                        Category category = appDb.getCategoryDao().getCategory(post.getCategory());
                        String categoryName = "";
                        if (category != null)
                            categoryName = category.getName();
                        simplePost.setCategory(categoryName);
                        simplePosts.add(simplePost);
                    }
                    return simplePosts;
                })
                .map(simplePosts -> {
                    List<SimplePost> fromDb = appDb.getPostDao().getAllPosts().getValue();
                    for (int i = 0; i < simplePosts.size(); i++) {
                        if (fromDb != null && fromDb.contains(simplePosts.get(i))) {
                            simplePosts.get(i).setBookmarked(true);
                        }
                    }
                    return simplePosts;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
