package dsdmsa.utmnews.presentation.presenters;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import dsdmsa.utmnews.data.network.OnDataLoaded;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsFragmentVP;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsFragmentVP.View> implements
        NewsFragmentVP.Presenter,
        OnDataLoaded<List<Post>> {
    @Override
    public void onSuccess(List<Post> response) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void loadNewsOnPage(int page) {

    }

    @Override
    public void shareString(String str) {

    }

    @Override
    public void bookmarkPost(SimplePost post) {

    }

    @Override
    public void getSearchedNewses(String searchKey, int perPage, int page) {

    }

    @Override
    public void getCategoryNewses(int categoryId, int perPage, int page) {

    }

    @Override
    public void getNewsByTag(int tagId, int perPage, int page) {

    }

    @Override
    public void removeBookmarkPost(SimplePost post) {

    }

//    @Inject
//    UtmServices services;
//
//    private boolean isFirstPage = false;
//
////    public NewsPresenter() {
////        App.getAppComponent().inject(this);
////    }
//
//    @Override
//    public void loadNewsOnPage(int page) {
//        getViewState().showProgressDialog();
//        services.getNews(page, ITEMS_PER_PAGE, this);
//        if (page == Constants.INITIAL_PAGE) {
//            isFirstPage = true;
//        }
//    }
//
//    @Override
//    public void getSearchedNewses(String searchKey, int perPage, int page) {
//        services.searchposts(searchKey, page, perPage, this);
//        getViewState().showProgressDialog();
//    }
//
//    @Override
//    public void getCategoryNewses(int categoryId, int perPage, int page) {
//        services.getNewsByCategory(categoryId, page, perPage, this);
//        getViewState().showProgressDialog();
//    }
//
//    @Override
//    public void getNewsByTag(int tagId, int perPage, int page) {
//        services.getNewsByTag(tagId, page, perPage, this);
//        getViewState().showProgressDialog();
//    }
//
//    @Override
//    public void removeBookmarkPost(SimplePost post) {
//    }
//
//    @Override
//    public void shareString(String str) {
//        Intent sendIntent = new Intent();
//        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, str);
//        sendIntent.setType(TEXT_PLAIN);
//        App.getAppComponent().getApp()
//                .startActivity(Intent.createChooser(
//                        sendIntent,
//                        App.getAppComponent().getApp().getString(R.string.share_title))
//                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                );
//    }
//
//    @Override
//    public void bookmarkPost(SimplePost post) {
//
//    }
//
////    @Override
////    public void bookmarkPost(SimplePost post) {
////        repository.add(post);
////    }
//
//    @Override
//    public void onSuccess(List<Post> response) {
//        new PostParser(response).start();
//    }
//
//    @Override
//    public void onError(String errorMsg) {
//        getViewState().hideProgressDialog();
//        getViewState().showInfoMessage(errorMsg);
//    }
//
//    public void setupRecyclerView() {
//        getViewState().setupRecyclerView();
//    }
//
//    private class PostParser extends Thread {
//        private List<Post> response;
//        private List<SimplePost> simplePosts = new ArrayList<>();
//
//        public PostParser(List<Post> response) {
//            this.response = response;
//        }
//
//        @Override
//        public void run() {
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            for (Post post : response) {
//                simplePosts.add(SimplePostAdapter.getSimplePost(post));
//            }
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    if (isFirstPage) {
//                        getViewState().refreshDatas(simplePosts);
//                        isFirstPage = false;
//                    } else {
//                        getViewState().addTags(simplePosts);
//                    }
//                    getViewState().hideProgressDialog();
//                }
//            });
//        }
//    }

}
