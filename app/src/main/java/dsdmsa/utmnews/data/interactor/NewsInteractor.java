package dsdmsa.utmnews.data.interactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class NewsInteractor {

    private UtmServices services;
    private AppDb appDb;
    private HashMap<Integer, String> catsegs = new HashMap<>();

    @Inject
    public NewsInteractor(UtmServices services, AppDb appDb) {
        this.services = services;
        this.appDb = appDb;
    }

    public void getNews(int page, int quantity, final Callback callback) {
        services.getNews(page, quantity, new OnDataLoaded<List<Post>>() {
            @Override
            public void onSuccess(final List<Post> response) {
                Single.fromCallable(new Callable<List<SimplePost>>() {
                    @Override
                    public List<SimplePost> call() throws Exception {
                        List<Category> categories = appDb.getCategoryDao().getAllCategories();
                        List<SimplePost> fromDb = appDb.getPostDao().getAllPosts().getValue();
                        List<SimplePost> simplePosts = new ArrayList<>();


                        for (Category category : categories) {
                            catsegs.put(category.id, category.name);
                        }


                        for (Post post : response) {
                            SimplePost simplePost = SimplePostAdapter.getSimplePost(post);
                            simplePost.setCategory(getCategory(post.categories.get(0)));
                            simplePosts.add(simplePost);
                        }
                        if (fromDb != null)
                            for (int i = 0; i < simplePosts.size(); i++) {
                                if (fromDb.contains(simplePosts.get(i))) {
                                    simplePosts.get(i).setBookmarked(true);
                                }
                            }
                        return simplePosts;
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<SimplePost>>() {
                            @Override
                            public void accept(List<SimplePost> simplePosts) throws Exception {
                                callback.onSuccess(simplePosts);
                            }
                        });
            }

            @Override
            public void onError(String errorMsg) {
                callback.onError(errorMsg);
            }
        });
    }

    private String getCategory(int id) {
        String cat = catsegs.get(id);
        return cat != null ? cat : "Noutati";
    }

    public interface Callback {
        void onSuccess(List<SimplePost> response);

        void onError(String errorMsg);
    }

}
