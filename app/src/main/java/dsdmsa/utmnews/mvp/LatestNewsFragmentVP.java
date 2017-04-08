package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.Post;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface LatestNewsFragmentVP {
    interface View extends MvpView,LoadingView {
        void showNews(List<Post> newses);
        void addNewses(List<Post> newses);
    }
    interface Presenter{
        void getNews();
        void loadMoreNews(int page);
    }
}
