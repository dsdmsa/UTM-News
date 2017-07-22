package dsdmsa.utmnews.data.interactor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class NewsInteractor {

    private UtmServices services;

    @Inject
    public NewsInteractor(UtmServices services) {
        this.services = services;
    }

    public void getNews(int page, int quantity, final Callback callback) {
        services.getNews(page, quantity, new OnDataLoaded<List<Post>>() {
            @Override
            public void onSuccess(final List<Post> response) {


                Single.fromCallable(new Callable<List<SimplePost>>() {
                    @Override
                    public List<SimplePost> call() throws Exception {
                        List<SimplePost> simplePosts = new ArrayList<>();
                        for (Post post : response) {
                            simplePosts.add(SimplePostAdapter.getSimplePost(post));
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

    public interface Callback {
        void onSuccess(List<SimplePost> response);

        void onError(String errorMsg);
    }

}
