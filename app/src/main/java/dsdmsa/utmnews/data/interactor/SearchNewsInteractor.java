package dsdmsa.utmnews.data.interactor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.api.UtmApi;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SearchNewsInteractor {

    private AppDb appDb;
    private UtmApi api;

    @Inject
    public SearchNewsInteractor(UtmApi api, AppDb appDb) {
        this.api = api;
        this.appDb = appDb;
    }

    public Observable<List<SimplePost>> getNews(String key, int page, int quantity) {

        return api.getSearchPosts(key,quantity,page)
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


//        services.searchposts(key, page, quantity, new OnDataLoaded<List<Post>>() {
//            @Override
//            public void onSuccess(final List<Post> response) {
//
//                Single.fromCallable(new Callable<List<SimplePost>>() {
//                    @Override
//                    public List<SimplePost> call() throws Exception {
//                        List<Category> categories = appDb.getCategoryDao().getAllCategories();
//                        List<SimplePost> fromDb = appDb.getPostDao().getAllPosts().getValue();
//                        List<SimplePost> simplePosts = new ArrayList<>();
//
//                        for (Category category : categories) {
//                            catsegs.put(category.id, category.name);
//                        }
//
//                        for (Post post : response) {
//                            SimplePost simplePost = SimplePostAdapter.getSimplePost(post);
//                            simplePost.setCategory(getCategory(post.categories.get(0)));
//                            simplePosts.add(simplePost);
//                        }
//                        if (fromDb != null)
//                            for (int i = 0; i < simplePosts.size(); i++) {
//                                if (fromDb.contains(simplePosts.get(i))) {
//                                    simplePosts.get(i).setBookmarked(true);
//                                }
//                            }
//                        return simplePosts;
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<List<SimplePost>>() {
//                            @Override
//                            public void accept(List<SimplePost> simplePosts) throws Exception {
//                                callback.onSuccess(simplePosts);
//                            }
//                        });
//
//
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//                callback.onError(errorMsg);
//            }
//        });
    }

//    public interface Callback {
//        void onSuccess(List<SimplePost> response);
//
//        void onError(String errorMsg);
//    }
//
//    private String getCategory(int id) {
//        String cat = catsegs.get(id);
//        return cat != null ? cat : "Noutati";
//    }

}
